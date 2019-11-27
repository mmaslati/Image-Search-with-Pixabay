package com.mor.maslati.image.search.with.pixabay;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ImagesHandler implements View.OnScrollChangeListener {

    // Variables:
    public Context context;
    private Network network;

    private RecyclerView recyclerView;
    private ViewPager viewPager;

    ArrayList<JSONObject> imagesResults;

    private String currentQuery;
    private int    currentPageNum;


    //*******   Singleton   *******
    private static ImagesHandler INSTANCE = null;

    private ImagesHandler(Context context, RecyclerView recyclerView) {
        this.recyclerView   = recyclerView;
        this.context        = context;
        this.network        = new Network(context, this);

        this.recyclerView.setOnScrollChangeListener(this);
    }

    public static ImagesHandler getInstance(Context context, RecyclerView recyclerView) {
        if (INSTANCE == null) {
            INSTANCE = new ImagesHandler(context,recyclerView);
        }
        return(INSTANCE);
    }
    //*******  END Singleton   *******




    // PUBLIC METHODS
    //==================

    public void queryImages ( String query ){

        currentQuery    = query;
        currentPageNum  = 1;

        network.getImagesByQueryString(query, 1);
    }


    public void fillUpGrid ( ArrayList<JSONObject> imagesResults ){

        this.imagesResults = imagesResults;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ImageGridRecyclerViewAdapter adapter = new ImageGridRecyclerViewAdapter(context, imagesResults);
        adapter.setClickListener(new ImageGridRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, ImagePagerActivity.class);

                intent.putExtra("Position", position);

                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


        //ToDo: Call for Next-Page of search results and push to Grid.
    }


    void setImagesPager(ViewPager viewPager){

        this.viewPager = viewPager;

        this.viewPager.setAdapter(new PageViewAdapter(this));
    }

    void startImagesPagerWithImage(int position){
        Log.d("Mor", "Yup continue here...");

    }
}
