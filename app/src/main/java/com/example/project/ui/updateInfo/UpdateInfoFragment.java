package com.example.project.ui.updateInfo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project.R;
import com.example.project.databinding.FragmentUpdateInfoBinding;
import com.example.project.ui.home.HomeFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateInfoFragment extends Fragment {

    private UpdateInfoViewModel updateInfoViewModel;
    private FragmentUpdateInfoBinding binding;

    private int personId;
    private DatabaseReference personDB;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);
        personId = sharedPreferences.getInt("userId", -1);

        if (personId == -1)
            Log.e("UpdateInfoFragment", "No User Id found in sharedPreferences");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentUpdateInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the ViewModel
        updateInfoViewModel = new ViewModelProvider(this).get(UpdateInfoViewModel.class);

        // Initialize Firebase reference for the specific person
        personDB = FirebaseDatabase.getInstance().getReference("Person").child(String.valueOf(personId));

        // Load person data
        updateInfoViewModel.loadPersonData(personId);

        // Observe LiveData for person name
        updateInfoViewModel.getPersonName().observe(getViewLifecycleOwner(), name -> {
            binding.etEditPersonName.setText(name != null ? name : ""); // Handle potential null values
        });

        // Observe LiveData for person age
        updateInfoViewModel.getPersonAge().observe(getViewLifecycleOwner(), age -> {
            binding.etEditPersonAge.setText(age != null ? String.valueOf(age) : ""); // Handle potential null values
        });

        // Observe LiveData for person weight
        updateInfoViewModel.getPersonWeight().observe(getViewLifecycleOwner(), weight -> {
            binding.etEditPersonWeight.setText(weight != null ? String.valueOf(weight) : ""); // Handle potential null values
        });

        // Observe LiveData for person height
        updateInfoViewModel.getPersonHeight().observe(getViewLifecycleOwner(), height -> {
            binding.etEditPersonHeight.setText(height != null ? String.valueOf(height) : ""); // Handle potential null values
        });

        // Set up button listeners
        Button btnSave = binding.btnEditPersonSave;
        btnSave.setOnClickListener(v -> savePersonData());

        Button btnReturn = binding.btnEditPersonReturn;
        btnReturn.setOnClickListener(v -> goToMainMenu());

        return root;
    }

    private void goToMainMenu() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_main_menu);
        navController.navigate(R.id.nav_home);
    }

    private void savePersonData() {
        String name = binding.etEditPersonName.getText().toString().trim();
        int age = Integer.parseInt(binding.etEditPersonAge.getText().toString().trim());
        double weight = Double.parseDouble(binding.etEditPersonWeight.getText().toString().trim());
        double height = Double.parseDouble(binding.etEditPersonHeight.getText().toString().trim());

        personDB.child("name").setValue(name);
        personDB.child("age").setValue(age);
        personDB.child("weight").setValue(weight);
        personDB.child("height").setValue(height);

        Toast.makeText(getActivity(), "Person Data Save!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}