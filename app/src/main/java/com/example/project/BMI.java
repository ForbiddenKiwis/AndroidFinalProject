package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Person;

public class BMI extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTitle, tvHeight, tvWeight, tvBMI, tvBMIResult;
    private EditText etHeight, etWeight;
    private RadioGroup rgSystem;
    private RadioButton rbImperial, rbMetric;
    private Button btnCalculate, btnDisplayAllBMI, btnReturn, btnBmiClear;

    DatabaseReference UserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
    }

    private void initialize() {

        tvTitle = findViewById(R.id.tvTitle);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvBMI = findViewById(R.id.tvBMI);
        tvBMIResult = findViewById(R.id.tvBMIResult);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        rgSystem = findViewById(R.id.rgSystem);
        rbImperial = findViewById(R.id.rbImperial);
        rbMetric = findViewById(R.id.rbMetric);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReturn = findViewById(R.id.btnReturn);
        btnBmiClear = findViewById(R.id.btnBmiClear);

        btnCalculate.setOnClickListener(this);
        btnDisplayAllBMI.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        btnBmiClear.setOnClickListener(this);

        UserDatabase = FirebaseDatabase.getInstance().getReference("User");

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnCalculate) {
            calculateAndDisplayBMI(view);
        }


        if (id == R.id.btnReturn) {
            finish();
        }

        if (id == R.id.btnBmiClear) {
            clearFields(view);
        }
    }

    private void clearFields(View view) {
        etHeight.setText("");
        etWeight.setText("");

        tvBMIResult.setText("");
        tvBMI.setText("");

        rgSystem.clearCheck();

        tvBMIResult.setText("Fields cleared!");
    }

    private void calculateAndDisplayBMI(View view) {
        try {
            String heightInput = etHeight.getText().toString().trim();
            String weightInput = etWeight.getText().toString().trim();

            if (heightInput.isEmpty() || weightInput.isEmpty()) {
                tvBMIResult.setText("Please enter valid height and weight.");
                return;
            }

            float height = Float.parseFloat(heightInput);
            float weight = Float.parseFloat(weightInput);

            if (height <= 0 || weight <= 0) {
                tvBMIResult.setText("Height and weight must be greater than zero.");
                return;
            }

            boolean isMetric = rbMetric.isChecked();
            float bmi;

            if (isMetric) {
                bmi = weight / (height * height);
            } else {
                bmi = (weight / (height * height)) * 703;
            }

            tvBMIResult.setText(String.format("Your BMI is: %.2f", bmi));


        }catch (NumberFormatException e) {
            tvBMIResult.setText("Invalid input. Please enter numeric values for height and weight.");
        } catch (Exception e) {
            tvBMIResult.setText("An unexpected error occurred. Please try again.");
        }

    }
}