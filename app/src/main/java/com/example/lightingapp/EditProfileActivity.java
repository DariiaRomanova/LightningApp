package com.example.lightingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    private static final int RC_IMAGE_PICKER = 123;

    private ImageButton backToProfileImageButton;

    private ImageView editPhotoImageButton;
    private TextView deletePhotoTextView;

    private EditText usernameEditText;
    private RelativeLayout saveChangesClickableRelativeLayout;
    private RelativeLayout changeCategoriesClickableRelativeLayout;

    SharedPreferences userData;
    SharedPreferences.Editor editor;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
            assert data != null;
            final Uri imageUri = data.getData();
            editor.putString("userPhotoUrl", String.valueOf(imageUri));
            editor.apply();
            Glide.with(editPhotoImageButton.getContext())
                    .load(userData.getString("userPhotoUrl", String.valueOf(R.drawable.avatar)))
                    .into(editPhotoImageButton);
            editPhotoImageButton.setPadding(0, 0, 0, 0);
            deletePhotoTextView.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(EditProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditProfileActivity.this, ArticlesListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = userData.edit();

        backToProfileImageButton = findViewById(R.id.backToProfileImageButton);
        editPhotoImageButton = findViewById(R.id.editPhotoImageButton);
        deletePhotoTextView = findViewById(R.id.deletePhotoTextView);

        if (!(userData.contains("userPhotoUrl")) || (userData.getString("userPhotoUrl", "").equals(""))) {
            editPhotoImageButton.setBackgroundResource(R.drawable.circle_yellow);
            editPhotoImageButton.setImageResource(R.drawable.edit_photo_icon);
            editPhotoImageButton.setPadding(130, 130, 130, 130);
            deletePhotoTextView.setVisibility(View.INVISIBLE);
            /*Glide.with(editPhotoImageButton.getContext())
                    .load(R.drawable.edit_photo_icon)
                    .into(editPhotoImageButton);*/
        } else {
            Glide.with(editPhotoImageButton.getContext())
                    .load(userData.getString("userPhotoUrl", String.valueOf(R.drawable.edit_photo_icon)))
                    .into(editPhotoImageButton);
            deletePhotoTextView.setVisibility(View.VISIBLE);
        }

        usernameEditText = findViewById(R.id.usernameEditText);
        usernameEditText.setText(userData.getString("userName", "Username"));

        saveChangesClickableRelativeLayout = findViewById(R.id.saveChangesClickableRelativeLayout);
        changeCategoriesClickableRelativeLayout = findViewById(R.id.changeCategoriesClickableRelativeLayout);

        backToProfileImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        editPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Выберите изображение"),
                        RC_IMAGE_PICKER);
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameEditText.setTextColor(getResources().getColor(R.color.black));
                editor.putString("userName", usernameEditText.getText().toString().trim());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        changeCategoriesClickableRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ChooseCategoriesActivity.class);
                startActivity(intent);
            }
        });

        saveChangesClickableRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void deletePhoto(View view) {
        editor.putString("userPhotoUrl", "");
        editor.apply();
        editPhotoImageButton.setBackgroundResource(R.drawable.circle_yellow);
        editPhotoImageButton.setImageResource(R.drawable.edit_photo_icon);
        editPhotoImageButton.setPadding(130, 130, 130, 130);
        deletePhotoTextView.setVisibility(View.INVISIBLE);
    }
}