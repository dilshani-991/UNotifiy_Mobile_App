package com.example.unotify_mobileapplication_assignment.Screens;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsNewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView newsRecyclerView;
    NewsAdapter adapter;
    List<NewsItem> newsList;
    private static final int REQUEST_CODE_ADD_NEWS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_news);



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

            if (id == R.id.nav_sports) {
                return true;
            } else if (id == R.id.nav_academic) {
                startActivity(new Intent(this, AcademicNewsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_events) {
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
                new NewsItem(R.drawable.event1, "Pongal Vizha - 2024"),
                new NewsItem(R.drawable.event2, "Kamatha Event")
        );
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
            holder.image.setImageResource(item.imageRes);
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        static class Holder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;

            Holder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.newsImage);
                title = itemView.findViewById(R.id.newsTitle);
            }
        }
    }
}
