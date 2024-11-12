package com.example.project.ui.changePassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.project.databinding.FragmentChangePasswordBinding;

public class ChangePasswordFragment extends Fragment {

private FragmentChangePasswordBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ChangePasswordViewModel changePasswordViewModel =
                new ViewModelProvider(this).get(ChangePasswordViewModel.class);

    binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        changePasswordViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}