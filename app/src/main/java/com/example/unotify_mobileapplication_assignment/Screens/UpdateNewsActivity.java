package com.example.unotify_mobileapplication_assignment.Screens;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;

public class UpdateNewsActivity extends AppCompatActivity {

    EditText editTitle;
    ImageView imagePreview;
    Button btnSave;

    int imageRes;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);

        editTitle = findViewById(R.id.editTitle);
        imagePreview = findViewById(R.id.imagePreview);
        btnSave = findViewById(R.id.btnSave);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        imageRes = getIntent().getIntExtra("imageRes", 0);
        position = getIntent().getIntExtra("position", -1);

        editTitle.setText(title);
        if (imageRes != 0) {
            imagePreview.setImageResource(imageRes);
        }

        btnSave.setOnClickListener(v -> {
            String newTitle = editTitle.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("newTitle", newTitle);
            resultIntent.putExtra("imageRes", imageRes);
            resultIntent.putExtra("position", position);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
