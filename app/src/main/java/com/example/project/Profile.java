package com.example.project;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Person;

public class Profile extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView tvDisplayPersonNameData, tvDisplayPersonAgeData, tvDisplayPersonWeightData,
            tvDisplayPersonHeightData;
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
        btnDisplayPersonReturn = findViewById(R.id.btnDisplayPersonReturn);
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