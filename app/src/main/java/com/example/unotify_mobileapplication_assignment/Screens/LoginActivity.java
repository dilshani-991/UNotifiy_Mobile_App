package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button loginButton, createAccountButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);


        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Toast.makeText(this, "Entered email: " + email, Toast.LENGTH_LONG).show();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Firebase login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });



        TextView createAccountText = findViewById(R.id.createAccountText);

        createAccountText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }
}
