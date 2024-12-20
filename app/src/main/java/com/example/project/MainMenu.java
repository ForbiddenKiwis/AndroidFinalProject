package com.example.project;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.StringRes;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.databinding.ActivityMainMenuBinding;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.CookieHandler;

public class MainMenu extends CookieHandler implements View.OnClickListener{

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_USER_ID = "userId";

    private TextView tvUserName, tvLogOut;
    private ImageView imgVProfile;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMenuBinding binding;

    DatabaseReference personDatabase;

    SharedPreferences sharedPreferences;
    private int personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        initialize();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_update_info, R.id.nav_change_password)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.action_settings) goToSettings();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initialize() {
        tvUserName = binding.navView.getHeaderView(0).findViewById(R.id.tvUserName);
        tvLogOut = binding.navView.getHeaderView(0).findViewById(R.id.tvLogOut);
        imgVProfile = binding.navView.getHeaderView(0).findViewById(R.id.imgVProfile);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, binding.appBarMainMenu.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        imgVProfile.setOnClickListener(this);
        tvLogOut.setOnClickListener(this);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        personDatabase = FirebaseDatabase.getInstance().getReference("Person");

        // Keep the user Id throughout the app
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        personId = getIntent().getIntExtra("personId", -1);


        loadName(personId);
    }

    @Override
    public void onClick(View view) {

        imgVProfile.setOnClickListener(v -> goToProfile());
        tvLogOut.setOnClickListener(v-> Logout());
    }

    private void Logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppsPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainMenu.this, LoginActivity.class);
        Toast.makeText(this, "User Logout" , Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }


    private void goToProfile() {
        Intent intent = new Intent(MainMenu.this, Profile.class);
        intent.putExtra("personId",personId);
        startActivity(intent);
        finish();
    }

    private void goToSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
    }

    private void loadName(int personId) {
        personDatabase.child(String.valueOf(personId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);

                    tvUserName.setText(name);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.apply();
                } else {
                    Log.e("Initialize", "No data available for personId: " + personId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Initialize", "Database error: " + error.getMessage());
            }
        });

    }
}
