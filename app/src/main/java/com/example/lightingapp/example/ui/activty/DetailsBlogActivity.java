package com.example.lightingapp.example.ui.activty;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lightingapp.R;
import com.example.lightingapp.example.model.blog.Post;

public class DetailsBlogActivity extends AppCompatActivity {

    private ImageView articleImageView;
    private TextView categoryTextView;
    private TextView titleTextView;
    private TextView contentTextView;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        findViews();
        displayPostDetails();
    }

    private void findViews() {
        articleImageView = findViewById(R.id.posterArticleImageView);
        categoryTextView = findViewById(R.id.categoryTextView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        buttonBack = findViewById(R.id.backToArticleListImageButton);
    }

    private void displayPostDetails() {
        Post post = (Post) getIntent().getSerializableExtra("SELECTED_POST");

        if (post != null) {
            // Set the data to the views
            categoryTextView.setText(post.getCategoryName());
            titleTextView.setText(post.getTitle());
            contentTextView.setText(post.getContent());

            // Load the image using Glide or any other library
            Glide.with(this).load(post.getImage()).into(articleImageView);
        }
    }
}