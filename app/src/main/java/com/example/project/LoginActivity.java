package com.example.project;

import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.CookieHandler;
import model.User;

public class LoginActivity extends CookieHandler implements View.OnClickListener {

    // Cookies properties
    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_LAST_ACTIVITY = "lastActivity";

    private TextView tvCreateAccount, tvForgotPassword;
    private EditText etUserId, etPassword;
    private Button btnLogin;
    private CheckBox cbShowPassword;

    private DatabaseReference  UserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();
    }

    private void initialize() {
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        etUserId = findViewById(R.id.etUserIdLogin);
        etPassword = findViewById(R.id.etPasswordLogin);

        cbShowPassword = findViewById(R.id.cbShowPassword);
        btnLogin = findViewById(R.id.btnLogIn);

        btnLogin.setOnClickListener(this);
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> showPassword(isChecked));
        tvForgotPassword.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);

        UserDatabase = FirebaseDatabase.getInstance().getReference("User");

        disablePaste(etUserId);
        disablePaste(etPassword);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnLogIn) login(view);
        if (id == R.id.tvCreateAccount) goToCreatePage(view);
        if (id == R.id.tvForgotPassword) goToForgotPage(view);
    }

    // Disabled Clipboard for Security reason
    private void disablePaste(EditText editText) {
        editText.setLongClickable(false);
        editText.setOnTouchListener((v, event) -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip()) {
                clipboard.clearPrimaryClip();
            }
            return false;
        });
    }

    private void showPassword(boolean isChecked) {
        if (isChecked) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    private void goToForgotPage(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    private void goToCreatePage(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
        startActivity(intent);
    }

    private void login(View view) {
        try {
            // Get data for User
            int userId = Integer.parseInt(etUserId.getText().toString());
            String password = etPassword.getText().toString();

            // Referencing the User base on the UserId to check if it exist.
            DatabaseReference userRef = UserDatabase.child(String.valueOf(userId));

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        //Gets Password input
                        String storedPassword = snapshot.child("password").getValue(String.class);

                        //Verify if password is valid
                        if (storedPassword != null && storedPassword.equals(password)) {
                            User user = new User(userId, password);

                            // Keeps userId session throughout the Application
                            SharedPreferences sharedPreferences = getSharedPreferences(
                                    PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(KEY_USER_ID, userId); // Save userId
                            editor.putLong(KEY_LAST_ACTIVITY, System.currentTimeMillis());
                            editor.apply(); // Apply changes

                            // Goes to mainMenu activity when login is successful
                            Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                            intent.putExtra("personId",userId);
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar.make(view, "Error. Password Invalid",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(view,
                                "Error. User Id not found.", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "Database Error: "+error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (NumberFormatException e)  {
            Toast.makeText(this, "Invalid User ID",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}