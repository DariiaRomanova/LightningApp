package com.example.lightingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences userData;
    SharedPreferences.Editor editor;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = userData.edit();
        //baseUrl = getIntent().getStringExtra("baseUrl");

        RelativeLayout newsClickableRelativeLayout = findViewById(R.id.newsClickableRelativeLayout);
        RelativeLayout profileClickableRelativeLayout = findViewById(R.id.profileClickableRelativeLayout);
        RelativeLayout editProfileClickableRelativeLayout = findViewById(R.id.editProfileClickableRelativeLayout);
        RelativeLayout rateAppClickableRelativeLayout = findViewById(R.id.rateAppClickableRelativeLayout);
        RelativeLayout shareClickableRelativeLayout = findViewById(R.id.shareClickableRelativeLayout);
        RelativeLayout privacyClickableRelativeLayout = findViewById(R.id.privacyClickableRelativeLayout);

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(userData.getString("userName", "Username"));

        ImageView userPhotoImageView = findViewById(R.id.userPhotoImageView);
        if (!(userData.contains("userPhotoUrl")) || (userData.getString("userPhotoUrl", "").equals(""))) {
            userPhotoImageView.setImageResource(R.drawable.avatar);
        } else {
            Glide.with(userPhotoImageView.getContext())
                    .load(userData.getString("userPhotoUrl", String.valueOf(R.drawable.avatar)))
                    .into(userPhotoImageView);
        }

        newsClickableRelativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ArticlesListActivity.class);
            //intent.putExtra("baseUrl", baseUrl);
            startActivity(intent);
        });

        profileClickableRelativeLayout.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });

        editProfileClickableRelativeLayout.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class)));

        rateAppClickableRelativeLayout.setOnClickListener(v -> rateApp());

        shareClickableRelativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my favorite news app at: https://play.google.com/store/apps/details?id=" /*  + BuildConfig.APPLICATION_ID*/ );
            intent.setType("text/plain");
            startActivity(intent);
        });


        privacyClickableRelativeLayout.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PrivacyPolicyActivity.class)));
    }

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
}