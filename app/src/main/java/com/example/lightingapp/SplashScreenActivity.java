package com.example.lightingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lightingapp.LocalData.ArticleWithCategory;
import com.example.lightingapp.Model.Article;
import com.example.lightingapp.Service.APIService;
import com.example.lightingapp.Service.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    SharedPreferences userData;
    SharedPreferences baseUrlPref;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorBaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        baseUrlPref = this.getSharedPreferences("baseUrl", Context.MODE_PRIVATE);
        editor = userData.edit();
        editorBaseUrl = baseUrlPref.edit();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    if (isNetworkConnected() || isInternetAvailable()) {
                        Log.d("Whatsup", "You are online");
                        db = FirebaseFirestore.getInstance();
                        db.collection("articles")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Whatsup", "Result is ok");
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String key = "link";
                                                String baseUrl = document.getString(key);
                                                editorBaseUrl.putString("baseUrl", baseUrl);
                                                editorBaseUrl.apply();
                                                APIService service = RetrofitInstance.getApiService();
                                                Call<ArrayList<Article>> call = service.getArticles(baseUrl);
                                                call.enqueue(new Callback<ArrayList<Article>>() {
                                                    @Override
                                                    public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                                                        if (response.isSuccessful()) {
                                                            Log.d("Whatsup", "Okey, alright Success");
                                                            List<Article> articles = response.body();
                                                            if (response.code() == 200) {
                                                                Log.d("Whatsup", "Okey, alright 200");
                                                                if (userData.getString("Sport", "0").equals("1") ||
                                                                        userData.getString("Technology", "0").equals("1") ||
                                                                userData.getString("Science", "0").equals("1") ||
                                                                userData.getString("Music", "0").equals("1") ||
                                                                userData.getString("Food", "0").equals("1") ||
                                                                userData.getString("Business", "0").equals("1") ||
                                                                userData.getString("Games", "0").equals("1") ||
                                                                userData.getString("Travel", "0").equals("1") ||
                                                                userData.getString("Fashion", "0").equals("1")) {
                                                                    Log.d("Whatsup", "Okey, alright");
                                                                    Intent intent = new Intent(SplashScreenActivity.this, ArticlesListActivity.class);
                                                                    startActivity(intent);
                                                                } else if (userData.getString("Sport", "0").equals("0") &&
                                                                        userData.getString("Technology", "0").equals("0") &&
                                                                        userData.getString("Science", "0").equals("0") &&
                                                                        userData.getString("Music", "0").equals("0") &&
                                                                        userData.getString("Food", "0").equals("0") &&
                                                                        userData.getString("Business", "0").equals("0") &&
                                                                        userData.getString("Games", "0").equals("0") &&
                                                                        userData.getString("Travel", "0").equals("0") &&
                                                                        userData.getString("Fashion", "0").equals("0")) {
                                                                    Log.d("Whatsup", "Okey, not alright");
                                                                    Intent intent = new Intent(SplashScreenActivity.this, ChooseCategoriesActivity.class);
                                                                    //intent.putExtra("baseUrl", baseUrl);
                                                                    startActivity(intent);
                                                                } else if (userData.contains("NotFirstEntrance") && userData.getString("NotFirstEntrance", "True").equals("True")) {
                                                                    Intent intent = new Intent(SplashScreenActivity.this, ArticlesListActivity.class);
                                                                    //intent.putExtra("baseUrl", baseUrl);
                                                                    startActivity(intent); }
                                                            } else {
                                                                Log.w("ResponseCode", String.valueOf(response.code()));
                                                            }
                                                        } else {
                                                            Log.w("ResponseUnsuccess", "Something is wrong");
                                                        }

                                                    }

                                                    @Override
                                                    public void onFailure(Call<ArrayList<Article>> call, Throwable t) {

                                                    }
                                                });

                                            }
                                        } else {
                                            Log.w(TAG, "Error getting documents.", task.getException());
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(SplashScreenActivity.this, "You are offline", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}