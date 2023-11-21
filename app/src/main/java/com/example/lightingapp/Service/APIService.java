package com.example.lightingapp.Service;

import com.example.lightingapp.Model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {
    @GET
    Call<ArrayList<Article>> getArticles(@Url String url);
}

