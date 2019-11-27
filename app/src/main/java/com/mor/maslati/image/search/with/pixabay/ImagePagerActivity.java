package com.mor.maslati.image.search.with.pixabay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImagePagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImagesHandler imagesHandler;
    Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        closeButton = findViewById(R.id.close);

        imagesHandler = ImagesHandler.getInstance(this,null);

        viewPager = findViewById(R.id.imagePager);

        imagesHandler.setImagesPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        int firstImagePosition = intent.getIntExtra("Position",1);

        imagesHandler.startImagesPagerWithImage(firstImagePosition);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
