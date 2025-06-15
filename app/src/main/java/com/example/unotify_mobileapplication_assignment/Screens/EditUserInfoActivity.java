package com.example.unotify_mobileapplication_assignment.Screens;

import static com.example.unotify_mobileapplication_assignment.R.id.editInfoBtn;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;

public class EditUserInfoActivity extends AppCompatActivity {

    private TextView usernameDisplay, emailDisplay;
    private Button editInfoBtn, signOutBtn;

    private String username = "user name here";
    private String email = "user@email.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        usernameDisplay = findViewById(R.id.usernameDisplay);
        emailDisplay = findViewById(R.id.emailDisplay);
        editInfoBtn = findViewById(R.id.editInfoBtn);


        updateUserInfo();

        editInfoBtn.setOnClickListener(v -> showEditDialog());
        signOutBtn.setOnClickListener(v -> showSignOutConfirmation());
    }

    private void updateUserInfo() {
        usernameDisplay.setText("Username  : " + username);
        emailDisplay.setText("Email      : " + email);
    }

    private void showEditDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);

        EditText usernameInput = dialogView.findViewById(R.id.etUsername);
        EditText emailInput = dialogView.findViewById(R.id.etEmail);

        usernameInput.setText(username);
        emailInput.setText(email);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Edit Info")
                .setPositiveButton("OK", (dialog, which) -> {
                    username = usernameInput.getText().toString().trim();
                    email = emailInput.getText().toString().trim();
                    updateUserInfo();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showSignOutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
                    finish(); // or navigate to login
                })
                .setNegativeButton("No", null)
                .show();
    }
}

