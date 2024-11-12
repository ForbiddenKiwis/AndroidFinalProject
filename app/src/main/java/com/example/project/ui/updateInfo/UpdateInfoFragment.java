package com.example.project.ui.updateInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.project.databinding.FragmentUpdateInfoBinding;

public class UpdateInfoFragment extends Fragment {

private FragmentUpdateInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        UpdateInfoViewModel updateInfoViewModel =
                new ViewModelProvider(this).get(UpdateInfoViewModel.class);

    binding = FragmentUpdateInfoBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        updateInfoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}