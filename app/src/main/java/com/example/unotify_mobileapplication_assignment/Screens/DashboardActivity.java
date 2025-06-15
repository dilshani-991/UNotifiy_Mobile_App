package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;

public class DashboardActivity extends AppCompatActivity {

    Button newsBtn1, newsBtn2, newsBtn3;
    ImageView profileIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newsBtn1 = findViewById(R.id.newsBtn1);
        newsBtn2 = findViewById(R.id.newsBtn2);
        newsBtn3 = findViewById(R.id.newsBtn3);
        profileIcon = findViewById(R.id.profileIcon); // Corrected reference

        // Navigate to SportNewsActivity when newsBtn1 is clicked
        newsBtn1.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SportNewsActivity.class);
            startActivity(intent);
        });

        newsBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AcademicNewsActivity.class);
            startActivity(intent);
        });


        newsBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EventsNewsActivity.class);
            startActivity(intent);
        });


        profileIcon.setOnClickListener(this::showUserProfileMenu); // Attach dropdown to avatar
    }


    private void showUserProfileMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.user_profile_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.show();
    }

    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_user_info) {
            Toast.makeText(this, "User Info Clicked", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.menu_edit_user) {
            Toast.makeText(this, "Edit User Info Clicked", Toast.LENGTH_SHORT).show();

            // Call the dialog here instead of starting a new activity
            showEditUserDialog();

            return true;

        } else if (id == R.id.menu_sign_out) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Sign Out")
                    .setMessage("Really Want To Sign Out?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();

            return true;

        } else {
            return false;
        }
    }

    private void showEditUserDialog() {
        android.app.Dialog dialog = new android.app.Dialog(this);
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_user);
        dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.widget.EditText etUsername = dialog.findViewById(R.id.etUsername);
        android.widget.EditText etEmail = dialog.findViewById(R.id.etEmail);
        android.widget.Button btnOk = dialog.findViewById(R.id.btnOk);
        android.widget.Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Pre-fill with existing data if available
        etUsername.setText("John Doe"); // Replace with actual user data if available
        etEmail.setText("john.doe@example.com"); // Replace with actual user data if available

        btnOk.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass updated data to UserInfoActivity
            Intent intent = new Intent(this, UserInfoActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            startActivity(intent);

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
