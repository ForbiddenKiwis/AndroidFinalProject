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
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.BMI;
import com.example.project.R;
import com.example.project.databinding.FragmentHomeBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    DatabaseReference UserDatabase;

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

            final TextView textView = binding.textHome;
            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btnHomeBMI = binding.btnHomeBMI;
        btnHomeBMI.setOnClickListener(v -> {
            Log.d("HomeFragment", "Button clicked");
            goToBMI();
        });
            return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void goToBMI() {
        Intent intent = new Intent(requireActivity(), BMI.class);  // Use requireActivity() instead of getActivity()
        startActivity(intent);
    }


}