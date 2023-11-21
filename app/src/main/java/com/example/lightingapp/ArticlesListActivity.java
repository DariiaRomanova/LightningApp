package com.example.lightingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightingapp.Adapter.ArticleAdapter;
import com.example.lightingapp.LocalData.ArticleWithCategory;
import com.example.lightingapp.Model.Article;
import com.example.lightingapp.Service.APIService;
import com.example.lightingapp.Service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String baseUrl;
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout newsClickableRelativeLayout;
    private RelativeLayout profileClickableRelativeLayout;

    private TextView greetingTextView;

    private ArrayList<Article> articles;
    private ArrayList<ArticleWithCategory> articlesWithCategories = new ArrayList<>();

    private List<TextView> categoryTextViews = new ArrayList<>();

    SharedPreferences userData;
    SharedPreferences baseUrlPref;
    SharedPreferences.Editor editor;
    //SharedPreferences.Editor editorBaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        baseUrlPref = this.getSharedPreferences("baseUrl", Context.MODE_PRIVATE);
        editor = userData.edit();
        //editorBaseUrl = baseUrlPref.edit();

        categoryTextViews.add(findViewById(R.id.forYouTextView));
        categoryTextViews.add(findViewById(R.id.allTextView));
        categoryTextViews.add(findViewById(R.id.sportTextView));
        categoryTextViews.add(findViewById(R.id.technologyTextView));
        categoryTextViews.add(findViewById(R.id.scienceTextView));
        categoryTextViews.add(findViewById(R.id.musicTextView));
        categoryTextViews.add(findViewById(R.id.foodTextView));
        categoryTextViews.add(findViewById(R.id.businessTextView));
        categoryTextViews.add(findViewById(R.id.gamesTextView));
        categoryTextViews.add(findViewById(R.id.travelTextView));
        categoryTextViews.add(findViewById(R.id.fashionTextView));

        newsClickableRelativeLayout = findViewById(R.id.newsClickableRelativeLayout);
        profileClickableRelativeLayout = findViewById(R.id.profileClickableRelativeLayout);
        greetingTextView = findViewById(R.id.greetingTextView);
        String greeting = "Hello, " + userData.getString("userName", "Username");
        greetingTextView.setText(greeting);

        getArticleListFromServer();

        newsClickableRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                getArticleListFromServer();
            }
        });

        profileClickableRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticlesListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getArticleListFromServer() {
        baseUrl = baseUrlPref.getString("baseUrl", "baseUrl");
        APIService service = RetrofitInstance.getApiService();
        Call<ArrayList<Article>> call = service.getArticles(baseUrl);
        call.enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                articles = response.body();
                assert articles != null;
                addCategoryToArticle(articles);
                filterArticleByUserCategory();
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {

            }
        });
    }

    private void addCategoryToArticle(ArrayList<Article> articles) {

        for (int i = 0; i < articles.size(); i++) {
            ArticleWithCategory articleWithCategory = new ArticleWithCategory();
            articleWithCategory.setTitle(articles.get(i).getTitle().getRendered());
            articleWithCategory.setContent(articles.get(i).getContent().getRendered());
            articleWithCategory.setCategory(getCategory(i));
            articleWithCategory.setImgUrl(articles.get(i).getImg().getIsobar());
            articlesWithCategories.add(articleWithCategory);
        }
    }

    private String getCategory(int i) {
        switch (i) {
            case 1:
            case 10:
            case 19:
            case 28:
                return "Sport";
            case 2:
            case 11:
            case 20:
            case 29:
                return "Technology";
            case 3:
            case 12:
            case 21:
            case 30:
                return "Science";
            case 4:
            case 13:
            case 22:
                return "Music";
            case 5:
            case 14:
            case 23:
                return "Food";
            case 6:
            case 15:
            case 24:
                return "Business";
            case 7:
            case 16:
            case 25:
                return "Games";
            case 8:
            case 17:
            case 26:
                return "Travel";
            case 9:
            case 18:
            case 27:
                return "Fashion";
            default: return "Music";
        }
    }
    //}

    private void fillRecyclerView(ArrayList<ArticleWithCategory> articlesWithCategories) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new ArticleAdapter(articlesWithCategories, this);
        layoutManager = new LinearLayoutManager(ArticlesListActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    // Method to filter articles by category
    private void filterArticlesByCategory(String category) {
        ArrayList<ArticleWithCategory> filteredArticles = new ArrayList<>();
        for (ArticleWithCategory article : articlesWithCategories) {
            if (article.getCategory().equals(category)) {
                filteredArticles.add(article);
            }
        }
        fillRecyclerView(filteredArticles);
    }

    private void filterArticleByUserCategory() {
        ArrayList<ArticleWithCategory> filteredArticlesByUser = new ArrayList<>();
        for (ArticleWithCategory article : articlesWithCategories) {
            if (userData.getString(article.getCategory(), "0").equals("1")) {
                filteredArticlesByUser.add(article);
            }
        }
        TextView forYouTextView = findViewById(R.id.forYouTextView);
        forYouTextView.setTextColor(getResources().getColor(R.color.black));
        fillRecyclerView(filteredArticlesByUser);
    }


    /* public void onCategoryClicked(View view) {
        TextView item = view.findViewWithTag("item");
        switch (item.getText().toString()) {
            case "For you":
                filterArticleByUserCategory(item);
                item.setTextColor(getResources().getColor(R.color.black));
            case "All":
                fillRecyclerView(articlesWithCategories);
                item.setTextColor(getResources().getColor(R.color.black));
            case "Sport":
                Log.d("Whatsup", item.getText().toString());
                filterArticlesByCategory("Sport");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Technology":
                filterArticlesByCategory("Technology");
                Log.d("Whatsup", "Bam");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Science":
                filterArticlesByCategory("Science");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Music":
                filterArticlesByCategory("Music");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Food":
                filterArticlesByCategory("Food");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Business":
                filterArticlesByCategory("Business");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Games":
                filterArticlesByCategory("Games");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Travel":
                filterArticlesByCategory("Travel");
                item.setTextColor(getResources().getColor(R.color.black));
            case "Fashion":
                filterArticlesByCategory("Fashion");
                item.setTextColor(getResources().getColor(R.color.black));
            default:
                fillRecyclerView(articlesWithCategories);
        }
    }
    */

    public void onCategorySportClicked(View view) {
        filterArticlesByCategory("Sport");
        changeCategoryUI(view);
    }

    public void onForYouClicked(View view) {
        filterArticleByUserCategory();
        changeCategoryUI(view);
    }

    public void onAllCategoryClicked(View view) {
        fillRecyclerView(articlesWithCategories);
        changeCategoryUI(view);
    }

    public void onTechnologyCategoryClicked(View view) {
        filterArticlesByCategory("Technology");
        changeCategoryUI(view);
    }

    public void onScienceCategoryClicked(View view) {
        filterArticlesByCategory("Science");
        changeCategoryUI(view);
    }

    public void onMusicCategoryClicked(View view) {
        filterArticlesByCategory("Music");
        changeCategoryUI(view);
    }

    public void onFoodCategoryClicked(View view) {
        filterArticlesByCategory("Food");
        changeCategoryUI(view);
    }

    public void onBusinessCategoryClicked(View view) {
        filterArticlesByCategory("Business");
        changeCategoryUI(view);
    }

    public void onGamesCategoryClicked(View view) {
        filterArticlesByCategory("Games");
        changeCategoryUI(view);
    }

    public void onTravelCategoryClicked(View view) {
        filterArticlesByCategory("Travel");
        changeCategoryUI(view);
    }

    public void onFashionCategoryClicked(View view) {
        filterArticlesByCategory("Fashion");
        changeCategoryUI(view);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ArticlesListActivity.this, SplashScreenActivity.class);
        startActivity(intent);
    }

    private void changeCategoryUI(View view) {
        for (TextView textView : categoryTextViews) {
            if (textView.getId() == view.getId()) {
                textView.setTextColor(getResources().getColor(R.color.black));
            } else {
                textView.setTextColor(getResources().getColor(R.color.gray));
            }
        }

    }
}