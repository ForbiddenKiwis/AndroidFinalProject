package com.example.project.ui.changePassword;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project.R;
import com.example.project.databinding.FragmentChangePasswordBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel changePasswordViewModel;
    private FragmentChangePasswordBinding binding;

    private int userId;
    private DatabaseReference userDB;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1)
            Log.e("UpdateInfoFragment", "No User Id found in sharedPreferences");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        changePasswordViewModel =
                new ViewModelProvider(this).get(ChangePasswordViewModel.class);

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userDB = FirebaseDatabase.getInstance().getReference("User").child(String.valueOf(userId));

        changePasswordViewModel.loadUserData(userId);

        changePasswordViewModel.getUserId().observe(getViewLifecycleOwner(), id -> {
            binding.tvEditUserUserId.setText(userId != -1 ? String.valueOf(id) : "");
        });

        Button btnEditUserBack = binding.btnEditUserBack;
        btnEditUserBack.setOnClickListener(v -> goHome());

        Button btnEditUserSave = binding.btnEditUserSave;
        btnEditUserSave.setOnClickListener(v -> save());

        return root;
    }

    private void save() {
        String password = binding.etUserPassword.getText().toString().trim();

        userDB.child("password").setValue(password);

        Toast.makeText(getActivity(), "Person Data Save!", Toast.LENGTH_SHORT).show();
    }

    private void goHome() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_main_menu);
        navController.navigate(R.id.nav_home);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}