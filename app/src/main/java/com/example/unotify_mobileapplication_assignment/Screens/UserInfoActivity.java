package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class UserInfoActivity extends AppCompatActivity {

    private TextView txtUsername, txtEmail, txtAddress, txtContact;
    private Button btnUpdate, btnDelete, btnExit;
    private FirebaseAuth mAuth;

    private DatabaseReference userRef;
    private String userId;

    private static final int EDIT_USER_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtContact = findViewById(R.id.txtContact);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);

        // Firebase setup
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        loadUserInfo();

        btnUpdate.setOnClickListener(v -> {
            String username = txtUsername.getText().toString().replace("Username: ", "").trim();
            String email = txtEmail.getText().toString().replace("Email: ", "").trim();
            String address = txtAddress.getText().toString().replace("Address: ", "").trim();
            String contact = txtContact.getText().toString().replace("Contact: ", "").trim();

            Intent intent = new Intent(UserInfoActivity.this, EditUserInfoActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            intent.putExtra("address", address);
            intent.putExtra("contact", contact);
            startActivityForResult(intent, EDIT_USER_REQUEST);
        });

        btnDelete.setOnClickListener(v -> {
            userRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    finish();
                } else {
                    Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnExit.setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtUsername.setText("Username: " + snapshot.child("username").getValue(String.class));
                    txtEmail.setText("Email: " + snapshot.child("email").getValue(String.class));
                    txtAddress.setText("Address: " + snapshot.child("address").getValue(String.class));
                    txtContact.setText("Contact: " + snapshot.child("contact").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserInfoActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Receive updated user info and save it to Firebase and UI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_USER_REQUEST && resultCode == RESULT_OK && data != null) {
            String updatedUsername = data.getStringExtra("username");
            String updatedEmail = data.getStringExtra("email");
            String updatedAddress = data.getStringExtra("address");
            String updatedContact = data.getStringExtra("contact");

            txtUsername.setText("Username: " + updatedUsername);
            txtEmail.setText("Email: " + updatedEmail);
            txtAddress.setText("Address: " + updatedAddress);
            txtContact.setText("Contact: " + updatedContact);

            // ✅ Save back to Firebase
            userRef.child("username").setValue(updatedUsername);
            userRef.child("email").setValue(updatedEmail);
            userRef.child("address").setValue(updatedAddress);
            userRef.child("contact").setValue(updatedContact);

            Toast.makeText(this, "User info updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
