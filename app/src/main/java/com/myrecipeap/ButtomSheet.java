package com.myrecipeap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ButtomSheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet); // Replace with your actual layout file name

        LinearLayout privacyPolicy = findViewById(R.id.privacy_policy);
        LinearLayout logout = findViewById(R.id.about_dev);

        // Set up click listener for Privacy Policy
        privacyPolicy.setOnClickListener(v -> {
            // Open Privacy Policy Activity or URL
            Intent intent = new Intent(ButtomSheet.this, PrivacyPolicy.class);
            startActivity(intent);
        });

        // Set up click listener for Logout
        logout.setOnClickListener(v -> logout());
    }

    private void logout() {
        // Clear user session data
        SharedPreferences sharedPreferences = getSharedPreferences("YourPrefsName", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all saved data
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(ButtomSheet.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
        startActivity(intent);
        finish(); // Close the current Activity
    }
}

