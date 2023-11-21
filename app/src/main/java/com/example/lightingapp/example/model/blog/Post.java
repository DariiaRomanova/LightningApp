package com.example.lightingapp.example.model.blog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    private String id;
    @SerializedName("category_id")
    private String categoryId;
    private String title;
    private String content;
    private String image; // Added image field

    private String categoryName;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() { // Getter for image
        return image;
    }

    public void setImage(String image) { // Setter for image
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Post{" + "id='" + id + '\'' + ", categoryId='" + categoryId + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + ", image='" + image + '\'' + '}';
    }
}