package com.example.project.userweight;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
@IgnoreExtraProperties
public class UserWeight {
    private int weight;
    private int userId;
    private String dateTimeAdded;

    public UserWeight(int weight, int userId) {
        this.weight = weight;
        this.userId = userId;
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

        this.dateTimeAdded = ISO_8601_FORMAT.format(new Date());

    }

    public UserWeight(int weight, int userId, String dateTimeAdded) {
        this.weight = weight;
        this.userId = userId;
        this.dateTimeAdded = dateTimeAdded;
    }

    public UserWeight(){}

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDateTimeAdded() {
        return dateTimeAdded;
    }

    public void setDateTimeAdded(String dateTimeAdded) {
        this.dateTimeAdded = dateTimeAdded;
    }

}
