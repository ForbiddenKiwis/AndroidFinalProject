package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.FirebaseApp;

import model.Person;

public class Profile extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView tvDisplayPersonNameData, tvDisplayPersonAgeData, tvDisplayPersonWeightData,
            tvDisplayPersonHeightData, tvDeleteAccount;
    Button btnDisplayPersonReturn;

    DatabaseReference personDatabase;

    SharedPreferences sharedPreferences;
    private int personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();
    }

    private void initialize(){
        tvDisplayPersonNameData = findViewById(R.id.tvDisplayPersonNameData);
        tvDisplayPersonAgeData = findViewById(R.id.tvDisplayPersonAgeData);
        tvDisplayPersonWeightData = findViewById(R.id.tvDisplayPersonWeightData);
        tvDisplayPersonHeightData = findViewById(R.id.tvDisplayPersonHeightData);

        tvDeleteAccount = findViewById(R.id.tvDeleteAccount);

        btnDisplayPersonReturn = findViewById(R.id.btnDisplayPersonReturn);

        tvDeleteAccount.setOnClickListener(this);
        btnDisplayPersonReturn.setOnClickListener(this);

        personDatabase = FirebaseDatabase.getInstance().getReference("Person");

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        personId = getIntent().getIntExtra("personId", -1);
        getPersonFromDatabase(personId);

        displayPersonInfo();
    }

    private void displayPersonInfo() {
        if (personId != -1) {
            personDatabase.child(String.valueOf(personId)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        Integer age = snapshot.child("age").getValue(Integer.class);
                        Double weight = snapshot.child("weight").getValue(Double.class);
                        Double height = snapshot.child("height").getValue(Double.class);

                        tvDisplayPersonNameData.setText(name != null ? name : "Name not available");
                        tvDisplayPersonAgeData.setText(age != null ? String.valueOf(age) : "Age " +
                                "not " +
                                "available");
                        tvDisplayPersonWeightData.setText(weight != null ? String.valueOf(weight) :
                                "Weight not available");
                        tvDisplayPersonHeightData.setText(height != null ? String.valueOf(height) :
                                "Height not available");
                    } else {
                        Toast.makeText(Profile.this, "Person Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, "Error: " + error.getMessage() ,
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnDisplayPersonReturn) goToMainMenuActivity();
        if (id == R.id.tvDeleteAccount) deleteAccount();
    }

    private void deleteAccount() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account and erase all data?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (personId != -1) {
                        // Reference to the Firebase database
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                        // Delete person data
                        databaseReference.child("Person").child(String.valueOf(personId)).removeValue()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Delete user data from User table
                                        databaseReference.child("User").child(String.valueOf(personId)).removeValue()
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        // Clear shared preferences
                                                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.clear();
                                                        editor.apply();

                                                        // Redirect to the login page
                                                        Intent intent = new Intent(Profile.this, LoginActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(Profile.this, "Failed to delete user data from User table.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Profile.this, "Failed to delete person data from Person table.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(Profile.this, "Person ID not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }


    private void goToMainMenuActivity() {
        Intent intent = new Intent(Profile.this, MainMenu.class);
        intent.putExtra("personId",personId);
        startActivity(intent);
        finish();
    }

    private void getPersonFromDatabase(int userId){
        personDatabase.child(String.valueOf(userId)).addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String name = snapshot.child("name").getValue().toString();
            String age = snapshot.child("age").getValue().toString();
            String weight = snapshot.child("weight").getValue().toString();
            String height = snapshot.child("height").getValue().toString();
            tvDisplayPersonNameData.setText(name);
            tvDisplayPersonAgeData.setText(age);
            tvDisplayPersonWeightData.setText(weight);
            tvDisplayPersonHeightData.setText(height);

        }
        else{
            Toast.makeText(this, "No document", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}