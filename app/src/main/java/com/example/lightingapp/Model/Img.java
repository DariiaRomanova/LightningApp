package com.example.lightingapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {
    @SerializedName("isobar")
    @Expose
    private String isobar;

    public String getIsobar() {
        return isobar;
    }

    public void setIsobar(String isobar) {
        this.isobar = isobar;
    }

}
