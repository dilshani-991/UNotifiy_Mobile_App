package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Import your app's R file here:
import com.example.unotify_mobileapplication_assignment.R;

public class EditUserInfoActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etAddress, etContact;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        btnSave = findViewById(R.id.btnSave);

        // Receive data from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String address = intent.getStringExtra("address");
        String contact = intent.getStringExtra("contact");

        // Set data to EditTexts
        etUsername.setText(username);
        etEmail.setText(email);
        etAddress.setText(address);
        etContact.setText(contact);

        btnSave.setOnClickListener(v -> {
            String newUsername = etUsername.getText().toString().trim();
            String newEmail = etEmail.getText().toString().trim();
            String newAddress = etAddress.getText().toString().trim();
            String newContact = etContact.getText().toString().trim();

            if (TextUtils.isEmpty(newUsername)) {
                etUsername.setError("Username required");
                etUsername.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(newEmail)) {
                etEmail.setError("Email required");
                etEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(newAddress)) {
                etAddress.setError("Address required");
                etAddress.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(newContact)) {
                etContact.setError("Contact required");
                etContact.requestFocus();
                return;
            }

            // TODO: Save the updated user info to database or Firebase here

            // Send back updated info to caller
            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", newUsername);
            resultIntent.putExtra("email", newEmail);
            resultIntent.putExtra("address", newAddress);
            resultIntent.putExtra("contact", newContact);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "User info updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
