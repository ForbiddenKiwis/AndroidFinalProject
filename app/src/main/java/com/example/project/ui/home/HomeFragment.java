package com.example.project.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project.BMI;
import com.example.project.R;
import com.example.project.databinding.FragmentHomeBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button btnGoToBMICalculator, btnGoToBMIDisplay, btnGoToBMIMagazine;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        btnGoToBMICalculator = binding.btnGoToBMICalculator;
        btnGoToBMICalculator.setOnClickListener(view -> goDisply());

        final Button btnGoToBMICalculator = binding.btnGoToBMICalculator;
        btnGoToBMICalculator.setOnClickListener(view -> goBMI());

        btnGoToBMIMagazine = binding.btnGoToBMIMagazine;
        btnGoToBMIMagazine.setOnClickListener(View -> goDisply());

        final Button btnGoToBMIMagazine = binding.btnGoToBMIMagazine;


        return root;
    }

    private void goDisply() {
    }

    private void goBMI() {
        Intent intent = new Intent(requireActivity(), BMI.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}