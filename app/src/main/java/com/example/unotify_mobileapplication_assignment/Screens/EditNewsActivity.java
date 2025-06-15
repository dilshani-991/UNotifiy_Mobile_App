package com.example.unotify_mobileapplication_assignment.Screens;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unotify_mobileapplication_assignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditNewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView newsRecyclerView;
    NewsAdapter adapter;
    List<NewsItem> newsList;
    private static final int REQUEST_CODE_ADD_NEWS = 1;
    private static final int REQUEST_CODE_EDIT_NEWS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_news);

        FloatingActionButton fab = findViewById(R.id.fabAddNews);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(EditNewsActivity.this, AddNewsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_NEWS);
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sport News");
        }

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>(getSampleNews());
        adapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(adapter);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.nav_sports);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_sports) return true;
            if (id == R.id.nav_academic) {
                startActivity(new Intent(this, AcademicNewsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            if (id == R.id.nav_events) {
                startActivity(new Intent(this, EventsNewsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private List<NewsItem> getSampleNews() {
        return Arrays.asList(
                new NewsItem(R.drawable.karate, "University Karate (Kata) Team Shines At The National Karate Championship - 2021"),
                new NewsItem(R.drawable.taekwondo, "UNIVERSITY OF COLOMBO STUDENTS WON MEDALS IN SAG 2019"),
                new NewsItem(R.drawable.karate, "University Karate (Kata) Team Shines At The National Karate Championship - 2021")
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD_NEWS) {
                String title = data.getStringExtra("title");
                String content = data.getStringExtra("content");
                String imageUriStr = data.getStringExtra("imageUri");

                Uri imageUri = imageUriStr != null ? Uri.parse(imageUriStr) : null;
                newsList.add(new NewsItem(imageUri, title, content));
                adapter.notifyItemInserted(newsList.size() - 1);
            } else if (requestCode == REQUEST_CODE_EDIT_NEWS) {
                String newTitle = data.getStringExtra("newTitle");
                int pos = data.getIntExtra("position", -1);
                int imageRes = data.getIntExtra("imageRes", 0);
                if (pos != -1) {
                    NewsItem updated = new NewsItem(imageRes, newTitle);
                    newsList.set(pos, updated);
                    adapter.notifyItemChanged(pos);
                }
            }
        }
    }

    static class NewsItem {
        Uri imageUri;
        int imageRes;
        String title;
        String content;

        NewsItem(Uri imageUri, String title, String content) {
            this.imageUri = imageUri;
            this.imageRes = -1;
            this.title = title;
            this.content = content;
        }

        NewsItem(int imageRes, String title) {
            this.imageUri = null;
            this.imageRes = imageRes;
            this.title = title;
            this.content = "";
        }
    }

    static class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {

        List<NewsItem> newsList;

        NewsAdapter(List<NewsItem> list) {
            this.newsList = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            NewsItem item = newsList.get(position);
            holder.title.setText(item.title);

            if (item.imageUri != null) {
                holder.image.setImageURI(item.imageUri);
            } else {
                holder.image.setImageResource(item.imageRes);
            }

            holder.editButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), UpdateNewsActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("imageRes", item.imageRes);
                intent.putExtra("position", holder.getAdapterPosition());

                if (v.getContext() instanceof Activity) {
                    ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_CODE_EDIT_NEWS);
                }
            });


            holder.deleteButton.setOnClickListener(v -> {
                int pos = holder.getAdapterPosition();
                newsList.remove(pos);
                notifyItemRemoved(pos);
                Toast.makeText(v.getContext(), "Deleted: " + item.title, Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        static class Holder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            Button editButton, deleteButton;

            Holder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.newsImage);
                title = itemView.findViewById(R.id.newsTitle);
                editButton = itemView.findViewById(R.id.editButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}
