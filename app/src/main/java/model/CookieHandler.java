package model;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.LoginActivity;

public class CookieHandler extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_LAST_ACTIVITY = "lastActivity";
    private static final long TIMEOUT_DURATION = 180000; // 1 minute timeout

    private SharedPreferences sharedPreferences;
    private Handler inactivityHandler = new Handler();
    private Runnable inactivityRunnable = new Runnable() {
        @Override
        public void run() {
            // If user has been inactive for too long, log them out
            logOut();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSessionTimeout();
        // Reset the inactivity timer every time the activity is resumed
        inactivityHandler.removeCallbacks(inactivityRunnable);
        inactivityHandler.postDelayed(inactivityRunnable, TIMEOUT_DURATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancel the inactivity check when the activity is paused (user is not interacting)
        inactivityHandler.removeCallbacks(inactivityRunnable);
    }

    // Check session timeout on every activity resume
    private void checkSessionTimeout() {
        int userId = sharedPreferences.getInt(KEY_USER_ID, -1); // -1 means user is not logged in
        if (userId != -1) {
            long lastActivity = sharedPreferences.getLong(KEY_LAST_ACTIVITY, 0);
            long currentTime = System.currentTimeMillis();

            // If the user hasn't interacted in the last 1 minute, log them out
            if (currentTime - lastActivity > TIMEOUT_DURATION) {
                logOut();
            } else {
                // Update the last activity timestamp
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(KEY_LAST_ACTIVITY, currentTime);
                editor.apply();
            }
        }
    }

    private void logOut() {
        // Clear user session (userId, last activity)
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_LAST_ACTIVITY);
        editor.apply();

        // Redirect to login activity
        Intent intent = new Intent(CookieHandler.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}