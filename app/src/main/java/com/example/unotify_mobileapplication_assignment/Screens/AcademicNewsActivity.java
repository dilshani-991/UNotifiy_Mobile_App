package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unotify_mobileapplication_assignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcademicNewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView newsRecyclerView;
    NewsAdapter adapter;
    List<NewsItem> newsList; // make it a member variable so we can update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_news);

        FloatingActionButton fab = findViewById(R.id.fabAddNews);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(AcademicNewsActivity.this, AddNewsActivity.class);
            startActivity(intent);
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Academic News");
        }

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list
        newsList = new ArrayList<>(getSampleNews());
        adapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(adapter);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.nav_academic); // Correct to academic here
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_sports) {
                startActivity(new Intent(this, SportNewsActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else if (id == R.id.nav_academic) {
                // Already here
                return true;
            } else if (id == R.id.nav_events) {
                startActivity(new Intent(this, EventsNewsActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            }
            return false;
        });
    }

    private List<NewsItem> getSampleNews() {
        return Arrays.asList(
                new NewsItem(R.drawable.academic1, "SHORT COURSE IN BIOSTATISTICS [DEADLINE: 20 July, 2024]"),
                new NewsItem(R.drawable.adademic2, "Educational Visit to 'Diyasaru Uyana’, Battaramulla")
        );
    }

    static class NewsItem {
        int imageRes;
        String title;
        NewsItem(int imageRes, String title) {
            this.imageRes = imageRes;
            this.title = title;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {

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
            holder.image.setImageResource(item.imageRes);

            holder.readMoreButton.setOnClickListener(v ->
                    Toast.makeText(v.getContext(), "Read more: " + item.title, Toast.LENGTH_SHORT).show()
            );

            holder.editButton.setOnClickListener(v -> {
                // Open Edit Activity or show edit dialog here
                Toast.makeText(v.getContext(), "Edit: " + item.title, Toast.LENGTH_SHORT).show();
                // Example: Intent intent = new Intent(v.getContext(), EditNewsActivity.class);
                // intent.putExtra("newsTitle", item.title);
                // v.getContext().startActivity(intent);
            });

            holder.deleteButton.setOnClickListener(v -> {
                // Remove item from list and notify adapter
                newsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, newsList.size());
                Toast.makeText(v.getContext(), "Deleted: " + item.title, Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            Button readMoreButton, editButton, deleteButton;

            Holder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.newsImage);
                title = itemView.findViewById(R.id.newsTitle);
                readMoreButton = itemView.findViewById(R.id.readMoreButton);
                editButton = itemView.findViewById(R.id.editButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}
