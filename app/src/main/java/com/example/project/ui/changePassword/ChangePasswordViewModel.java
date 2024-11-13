package com.example.project.ui.changePassword;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<Integer> userId = new MutableLiveData<>();
    private final  MutableLiveData<String> password = new MutableLiveData<>();
    private DatabaseReference userDB;

    public ChangePasswordViewModel() {
        userDB = FirebaseDatabase.getInstance().getReference("User");
    }

    public void loadUserData(int id){
        userDB.child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userId.setValue(snapshot.child("userId").getValue(Integer.class));
                    password.setValue(snapshot.child("age").getValue(String.class));

                } else {
                    Log.e("ChangePasswordViewModel", "No Data Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChangePasswordViewModel", "Database Error: "+ error.getMessage());
            }
        });
    }

    public LiveData<Integer> getUserId() {
        return userId;
    }
    public LiveData<String> getPassword() {return password;}
}