package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityHistory extends AppCompatActivity implements View.OnClickListener {

    TextView tvMKcVal, tvTuKcVal, tvWKcVal, tvThKcVal, tvFKcVal, tvSaKcVal, tvSuKcVal,
            tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;

    LinearLayout llDaily, llKcVal;
    View barM, barTu, barW, barTh, barF, barSa, barSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();
    }

    private void initialize() {
        tvMKcVal = findViewById(R.id.tvMKcVal);
        tvTuKcVal = findViewById(R.id.tvTuKcVal);
        tvWKcVal = findViewById(R.id.tvWKcVal);
        tvThKcVal = findViewById(R.id.tvThKcVal);
        tvFKcVal = findViewById(R.id.tvFKcVal);
        tvSaKcVal = findViewById(R.id.tvSaKcVal);
        tvSuKcVal = findViewById(R.id.tvSuKcVal);

        tvMonday = findViewById(R.id.tvMonday);
        tvTuesday = findViewById(R.id.tvTuesday);
        tvWednesday = findViewById(R.id.tvWednesday);
        tvThursday = findViewById(R.id.tvThursday);
        tvFriday = findViewById(R.id.tvFriday);
        tvSaturday = findViewById(R.id.tvSaturday);
        tvSunday = findViewById(R.id.tvSunday);

        llDaily = findViewById(R.id.llDaily);
        llKcVal = findViewById(R.id.llKcVal);

        barM = findViewById(R.id.barM);
        barTu = findViewById(R.id.barTu);
        barW = findViewById(R.id.barW);
        barTh = findViewById(R.id.barTh);
        barF = findViewById(R.id.barF);
        barSa = findViewById(R.id.barSa);
        barSu = findViewById(R.id.barSu);

        barM.setOnClickListener(this);
        barTu.setOnClickListener(this);
        barW.setOnClickListener(this);
        barTh.setOnClickListener(this);
        barF.setOnClickListener(this);
        barSa.setOnClickListener(this);
        barSu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.barM || id == R.id.barTu || id == R.id.barW || id == R.id.barTh || id == R.id.barF || id == R.id.barSa || id == R.id.barSu) goToActivityDescripion();
    }

    private void goToActivityDescripion() {
    }
}