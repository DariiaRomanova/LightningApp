package com.example.lightingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> startActivity(new Intent(PrivacyPolicyActivity.this, ProfileActivity.class)));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PrivacyPolicyActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}