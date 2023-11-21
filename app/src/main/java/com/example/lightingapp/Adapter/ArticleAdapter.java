package com.example.lightingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lightingapp.ArticleActivity;
import com.example.lightingapp.LocalData.ArticleWithCategory;
import com.example.lightingapp.Model.Article;
import com.example.lightingapp.Model.Content;
import com.example.lightingapp.R;

import java.util.ArrayList;
import java.util.Random;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<ArticleWithCategory> articles;
    private Context context;

    public ArticleAdapter (ArrayList<ArticleWithCategory> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleWithCategory article = articles.get(position);
        Glide.with(holder.articleImageView.getContext())
                .load(article.getImgUrl())
                .into(holder.articleImageView);
        holder.titleTextView.setText(article.getTitle());
        holder.categoryTextView.setText(article.getCategory());
        /*
        Glide.with(holder.articleImageView.getContext())
                .load(article.getImg().getIsobar())
                .into(holder.articleImageView);
        holder.titleTextView.setText(article.getTitle().getRendered());

        String[] categories = {"Sport", "Music", "Technology", "Science", "Food", "Business", "Games", "Travel", "Fashion"};
        Random random = new Random();
        int randomIndex = random.nextInt(categories.length);
        String randomCategory = categories[randomIndex];
        holder.categoryTextView.setText(randomCategory);

         */
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView posterCardView;
        public ImageView articleImageView;
        public TextView categoryTextView;
        public TextView titleTextView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            posterCardView = itemView.findViewById(R.id.posterCardView);
            articleImageView = itemView.findViewById(R.id.posterImageView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ArticleWithCategory article = articles.get(position);

            Intent intent = new Intent(context, ArticleActivity.class);

            intent.putExtra("imageResource", article.getImgUrl());
            intent.putExtra("articleTitle", article.getTitle());
            intent.putExtra("articleContent", article.getContent());
            /*
            intent.putExtra("imageResource", article.getImg().getIsobar());
            intent.putExtra("articleTitle", article.getTitle().getRendered());
            intent.putExtra("articleContent", article.getContent().getRendered());
             */
            context.startActivity(intent);
        }
    }
}
