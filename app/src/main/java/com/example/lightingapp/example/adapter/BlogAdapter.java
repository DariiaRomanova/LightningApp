package com.example.lightingapp.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lightingapp.R;
import com.example.lightingapp.example.model.blog.Post;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {

    private BlogAdapter.OnItemClickListener mListener;
    private ArrayList<Post> blogs = new ArrayList<>();

    public BlogAdapter(ArrayList<Post> blogs, BlogAdapter.OnItemClickListener mListener) {
        this.blogs = blogs;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BlogAdapter.BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        BlogAdapter.BlogViewHolder articleViewHolder = new BlogAdapter.BlogViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdapter.BlogViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public void setPosts(ArrayList<Post> newBlog) {
        blogs.clear();
        if (newBlog != null) {
            blogs.addAll(newBlog);
        }
        notifyDataSetChanged();
    }

    public List<Post> getPosts() {
        return blogs;
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public CardView posterCardView;
        public ImageView articleImageView;
        public TextView categoryTextView;
        public TextView titleTextView;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> mListener.onItemClicked(getAdapterPosition()));
            posterCardView = itemView.findViewById(R.id.posterCardView);
            articleImageView = itemView.findViewById(R.id.posterImageView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind(int position) {
            Post post = blogs.get(position);
            categoryTextView.setText(post.getCategoryName());
            titleTextView.setText(post.getTitle());
            Glide.with(itemView.getContext()).load(post.getImage()).into(articleImageView);
        }
    }

    //OnClick interface
    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}