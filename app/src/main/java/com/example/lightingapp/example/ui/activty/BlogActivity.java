package com.example.lightingapp.example.ui.activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightingapp.R;
import com.example.lightingapp.example.adapter.BlogAdapter;
import com.example.lightingapp.example.adapter.CategoryAdapter;
import com.example.lightingapp.example.model.blog.BlogResponse;
import com.example.lightingapp.example.model.blog.Category;
import com.example.lightingapp.example.model.blog.Post;
import com.example.lightingapp.example.repository.BlogRepository;
import com.example.lightingapp.example.utils.MapCategoryConverter;
import com.example.lightingapp.example.utils.NetworkUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class BlogActivity extends AppCompatActivity implements BlogAdapter.OnItemClickListener, CategoryAdapter.OnItemClickListener {

    private static final String TAG = "BlogActivity";

    private CategoryAdapter categoryAdapter;
    private BlogAdapter blogAdapter;

    // Views
    private RecyclerView recyclerBlog;
    private RecyclerView recyclerCategory;
    private ImageButton buttonBack;
    private MaterialButton buttonRetry;
    private CardView cardViewError;
    private CardView cardProgress;

    // This is the response from the API
    private BlogResponse blogResponse;

    // This is the repository that handles the API calls
    private BlogRepository blogRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        initVariables();
        findViews();
        setClicks();
        initRecyclerViews();
        loadBlog();
    }

    private void initVariables() {
        blogRepository = new BlogRepository();
        blogAdapter = new BlogAdapter(new ArrayList<>(), this::onItemClicked);
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this::onCategoryItemClicked);
    }

    private void findViews() {
        recyclerBlog = findViewById(R.id.recycler_view_blog);
        recyclerCategory = findViewById(R.id.recycler_view_categories);
        buttonBack = findViewById(R.id.image_button_back);
        buttonRetry = findViewById(R.id.button_retry);
        cardViewError = findViewById(R.id.card_error);
        cardProgress = findViewById(R.id.card_progress);
    }

    private void setClicks() {
        buttonBack.setOnClickListener(v -> finish());
        buttonRetry.setOnClickListener(v -> loadBlog());
    }

    private void initRecyclerViews() {
        recyclerBlog.setAdapter(blogAdapter);
        recyclerCategory.setAdapter(categoryAdapter);
    }

    private void loadBlog() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            onFailed();
            return;
        }

        onLoading();
        blogRepository.loadBlog("https://dicefact.space/test_new.json", new BlogRepository.BlogDataCallback() {
            @Override
            public void onDataLoaded(BlogResponse blogResponse) {
                BlogActivity.this.blogResponse = blogResponse;
                if (blogResponse.getCategories() != null) {
                    onCategoryFetched((ArrayList<Category>) blogResponse.getCategories());
                }
                if (blogResponse.getPosts() != null) {
                    // Map category names to posts (связываем ID категории с постами.)
                    // Ссылка на обьект автоматически обновляется, так что возвращать из mapCategoryNamesToPosts новый объект не нужно.
                    MapCategoryConverter.mapCategoryNamesToPosts(blogResponse);
                    onBlogFetched((ArrayList<Post>) blogResponse.getPosts());
                }
                onLoadingSuccess(blogResponse);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: " + t.getMessage());
                onFailed();
            }
        });
    }

    private void onBlogFetched(ArrayList<Post> posts) {
        blogAdapter.setPosts(posts);
    }

    private void onCategoryFetched(ArrayList<Category> categories) {
        Category allCategory = new Category();
        allCategory.setId("all"); // Use a unique identifier for the 'All' category
        allCategory.setName("All");
        // Optionally set a default image for the 'All' category
        allCategory.setImage("default_image_url");

        ArrayList<Category> modifiedCategories = new ArrayList<>();
        modifiedCategories.add(allCategory); // Add 'All' category as the first item
        modifiedCategories.addAll(categories); // Add the rest of the categories

        categoryAdapter.setCategories(modifiedCategories);
    }

    private void onLoading() {
        cardProgress.setVisibility(View.VISIBLE);
        cardViewError.setVisibility(View.GONE);
    }

    private void onFailed() {
        cardProgress.setVisibility(View.GONE);
        cardViewError.setVisibility(View.VISIBLE);
    }

    private void onLoadingSuccess(BlogResponse blog) {
        blogResponse = blog;
        cardProgress.setVisibility(View.GONE);
        cardViewError.setVisibility(View.GONE);
    }

    @Override
    public void onCategoryItemClicked(int position) {
        Log.d(TAG, "onCategoryItemClicked: " + position);
        //Select category
        categoryAdapter.setSelectedPosition(position);

        Category selectedCategory = categoryAdapter.getCategories().get(position);

        if (selectedCategory.getId().equals("all")) {
            // If 'All' category is selected, display all posts
            blogAdapter.setPosts(new ArrayList<>(blogResponse.getPosts()));
        } else {
            // Filter posts by the selected category
            ArrayList<Post> filteredPosts = new ArrayList<>();
            for (Post post : blogResponse.getPosts()) {
                if (post.getCategoryId().equals(selectedCategory.getId())) {
                    filteredPosts.add(post);
                }
            }
            blogAdapter.setPosts(filteredPosts);
        }
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "onItemClicked: " + position);
        Intent intent = new Intent(this, DetailsBlogActivity.class);
        Post selectedPost = blogAdapter.getPosts().get(position);

        // Assuming Post class is Parcelable or Serializable
        intent.putExtra("SELECTED_POST", selectedPost);
        startActivity(intent);
    }
}