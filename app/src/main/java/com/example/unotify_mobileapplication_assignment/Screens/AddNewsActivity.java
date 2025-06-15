package com.example.unotify_mobileapplication_assignment.Screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unotify_mobileapplication_assignment.R;

import java.io.IOException;

public class AddNewsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText titleInput, contentInput;
    ImageView imagePreview;
    Button saveButton, pickImageButton;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        titleInput = findViewById(R.id.editTextTitle);
        contentInput = findViewById(R.id.editTextContent);
        imagePreview = findViewById(R.id.imagePreview);
        saveButton = findViewById(R.id.buttonSave);
        pickImageButton = findViewById(R.id.buttonPickImage);

        pickImageButton.setOnClickListener(v -> openImagePicker());

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("content", content);
            if (imageUri != null) {
                resultIntent.putExtra("imageUri", imageUri.toString());
            }

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
