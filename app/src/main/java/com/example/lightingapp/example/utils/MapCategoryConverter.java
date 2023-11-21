package com.example.lightingapp.example.utils;

import com.example.lightingapp.example.model.blog.BlogResponse;
import com.example.lightingapp.example.model.blog.Category;
import com.example.lightingapp.example.model.blog.Post;

import java.util.HashMap;
import java.util.Map;

public class MapCategoryConverter {
    public static void mapCategoryNamesToPosts(BlogResponse blog) {
        Map<String, String> categoryIdToNameMap = new HashMap<>();
        for (Category category : blog.getCategories()) {
            categoryIdToNameMap.put(category.getId(), category.getName());
        }

        for (Post post : blog.getPosts()) {
            String categoryName = categoryIdToNameMap.get(post.getCategoryId());
            if (categoryName != null) {
                post.setCategoryName(categoryName);
            }
        }
    }
}
