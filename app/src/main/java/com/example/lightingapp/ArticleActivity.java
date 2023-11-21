package com.example.lightingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArticleActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ArticleActivity.this, ArticlesListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ImageView articleImageView = findViewById(R.id.posterArticleImageView);
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);
        ImageButton backToArticleListImageButton = findViewById(R.id.backToArticleListImageButton);

        Intent intent = getIntent();
        Glide.with(articleImageView.getContext())
                .load(intent.getStringExtra("imageResource"))
                .into(articleImageView);

        categoryTextView.setText("Category");
        titleTextView.setText(intent.getStringExtra("articleTitle"));
        contentTextView.setText(intent.getStringExtra("articleContent"));

        backToArticleListImageButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(ArticleActivity.this, ArticlesListActivity.class);
            startActivity(backIntent);
        });
    }
}