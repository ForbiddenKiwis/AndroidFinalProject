package com.example.project.userweight;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserWeightActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddUserWeight, btnViewUserWeight;
    EditText edId, edWeight;
    DatabaseReference UserWeightDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user_weight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();
    }


    private void initialize(){
        btnAddUserWeight = findViewById(R.id.btnAddUserWeight);
        btnViewUserWeight = findViewById(R.id.btnViewUserWeight);
        edId = findViewById(R.id.edAddUserWeightId);
        edWeight = findViewById(R.id.edAddUserWeight);

        btnViewUserWeight.setOnClickListener(this);
        btnAddUserWeight.setOnClickListener(this);
        this.UserWeightDatabase = FirebaseDatabase.getInstance().getReference("UserWeight");
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnAddUserWeight) Add();

        if (id == R.id.btnViewUserWeight) ViewUserWeight();
    }

    private void Add(){
        int weight = Integer.valueOf(edWeight.getText().toString().trim());
        int id = Integer.valueOf(edId.getText().toString().trim());
        UserWeight userWeight = new UserWeight(weight,id);
        UserWeightDatabase.push().setValue(userWeight);
        clearToken();
    }

    private void clearToken(){
        edWeight.setText("");
        edId.setText("");
    }

    private void ViewUserWeight(){

    }
}