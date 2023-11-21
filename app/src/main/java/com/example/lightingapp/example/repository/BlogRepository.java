package com.example.lightingapp.example.repository;

import com.example.lightingapp.example.api.BlogService;
import com.example.lightingapp.example.api.RetrofitClient;
import com.example.lightingapp.example.model.blog.BlogResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogRepository {

    private final BlogService blogService;

    public BlogRepository() {
        // Initialize Retrofit BlogService here
        blogService = RetrofitClient.getClient().create(BlogService.class);
    }

    public interface BlogDataCallback {
        void onDataLoaded(BlogResponse blogResponse);
        void onError(Throwable t);
    }

    public void loadBlog(String url, BlogDataCallback callback) {
        Call<BlogResponse> call = blogService.getBlog(url);
        call.enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(Call<BlogResponse> call, Response<BlogResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onDataLoaded(response.body());
                } else {
                    // Handle the case where the response is not successful
                    callback.onError(new RuntimeException("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<BlogResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    // If needed, you can add more methods here for other data operations
}