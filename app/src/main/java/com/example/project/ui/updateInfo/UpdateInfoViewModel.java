package com.example.project.ui.updateInfo;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateInfoViewModel extends ViewModel {

    private final MutableLiveData<String> personName = new MutableLiveData<>();
    private final MutableLiveData<Integer> personAge = new MutableLiveData<>();
    private final MutableLiveData<Double> personWeight = new MutableLiveData<>();
    private final MutableLiveData<Double> personHeight = new MutableLiveData<>();

    private final DatabaseReference personDB;

    public UpdateInfoViewModel() {
        personDB = FirebaseDatabase.getInstance().getReference("Person");
    }

    public void loadPersonData(int personId){
        personDB.child(String.valueOf(personId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    personName.setValue(snapshot.child("name").getValue(String.class));
                    personAge.setValue(snapshot.child("age").getValue(Integer.class));
                    personWeight.setValue(snapshot.child("weight").getValue(Double.class));
                    personHeight.setValue(snapshot.child("height").getValue(Double.class));
                } else {
                    Log.e("UpdateInfoViewModel", "No Data Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UpdateInfoViewModel", "Database Error: "+ error.getMessage());
            }
        });
    }

    public LiveData<String> getPersonName() {
        return personName;
    }

    public LiveData<Integer> getPersonAge(){
        return personAge;
    }

    public LiveData<Double> getPersonWeight() {
        return personWeight;
    }

    public LiveData<Double> getPersonHeight() {
        return personHeight;
    }
}