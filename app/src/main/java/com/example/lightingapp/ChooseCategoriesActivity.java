package com.example.lightingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChooseCategoriesActivity extends AppCompatActivity {

    SharedPreferences userData;
    SharedPreferences.Editor editor;
    ImageButton nextButton;
    //private String baseUrl;

    private List<RelativeLayout> categoryRelativeLayouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = userData.edit();
       // baseUrl = getIntent().getStringExtra("baseUrl");

        categoryRelativeLayouts.add(findViewById(R.id.sportCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.technologyCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.scienceCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.musicCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.foodCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.businessCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.gamesCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.travelCatRelativeLayout));
        categoryRelativeLayouts.add(findViewById(R.id.fashionCatRelativeLayout));

        changeCategoryUI();


        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseCategoriesActivity.this, ArticlesListActivity.class);
                // intent.putExtra("baseUrl", baseUrl);
                startActivity(intent);
            }
        });
        editor.putString("NotFirstEntrance", "True");
        editor.apply();
    }

    public void onCategoryClicked(View view) {
        ArrayList<View> children = new ArrayList<>();
        view.addChildrenForAccessibility(children);
        TextView child = (TextView) children.get(0);
        if (userData.getString(child.getText().toString(), "0").equals("0")) {
            editor.putString(child.getText().toString(), "1");
            editor.apply();
            view.setBackgroundResource(R.drawable.rectangle_16_yellow);
            child.setTextColor(getResources().getColor(R.color.white));
            child.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.lightning), null, null, null);
        } else {
            editor.putString(child.getText().toString(), "0");
            editor.apply();
            view.setBackgroundResource(R.drawable.rectangle_16_white);
            child.setTextColor(getResources().getColor(R.color.basic_yellow));
            child.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.lightning_yellow), null, null, null);
        }
            }
    private void changeCategoryUI() {
        for (RelativeLayout relativeLayout : categoryRelativeLayouts) {
            TextView childTextView = (TextView) relativeLayout.getChildAt(0);
            if (userData.getString(childTextView.getText().toString(), "0").equals("1")) {
                childTextView.setTextColor(getResources().getColor(R.color.white));
                relativeLayout.setBackgroundResource(R.drawable.rectangle_16_yellow);
                childTextView.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.lightning), null, null, null);
            } else {
                childTextView.setTextColor(getResources().getColor(R.color.basic_yellow));
                relativeLayout.setBackgroundResource(R.drawable.rectangle_16_white);
                childTextView.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.lightning_yellow), null, null, null);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChooseCategoriesActivity.this, ArticlesListActivity.class);
        startActivity(intent);
    }
}
