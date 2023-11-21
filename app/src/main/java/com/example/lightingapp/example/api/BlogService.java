package com.example.lightingapp.example.api;

import com.example.lightingapp.example.model.blog.BlogResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BlogService {
    @GET
    Call<BlogResponse> getBlog(@Url String url);
}