package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword, etEmail;
    Button btnSignup;
    TextView checkAccountAlready;
    FirebaseAuth mAuth;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnSignup);
        checkAccountAlready = findViewById(R.id.checkAccountAlready);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with Firebase sign up
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();
                                User user = new User(username, email);
                                userRef.child(uid).setValue(user)
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(this, LoginActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(this, "Database error: " + task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        checkAccountAlready.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    // Simple User model class
    public static class User {
        public String username, email;

        public User() {} // Required by Firebase

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
