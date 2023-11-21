package com.example.lightingapp.example.model.blog;

import java.io.Serializable;
import java.util.List;

public class BlogResponse implements Serializable {
    private List<Category> categories;
    private List<Post> posts;

    // Getter
    public List<Category> getCategories() {
        return categories;
    }

    // Setter
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // Getter
    public List<Post> getPosts() {
        return posts;
    }

    // Setter
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "categories=" + categories +
                ", posts=" + posts +
                '}';
    }
}
