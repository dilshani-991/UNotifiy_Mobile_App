package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;

public class UserInfoActivity extends AppCompatActivity {

    Button exitButton;
    TextView usernameText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize views
        exitButton = findViewById(R.id.exitButton);
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);

        // Get Intent extras
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        // Set text if data received, else show default text
        if (username != null && !username.isEmpty()) {
            usernameText.setText("Username  : " + username);
        } else {
            usernameText.setText("Username  : user name here");
        }

        if (email != null && !email.isEmpty()) {
            emailText.setText("Email  : " + email);
        } else {
            emailText.setText("Email  : email@example.com");
        }

        exitButton.setOnClickListener(v -> finish());
    }
}
