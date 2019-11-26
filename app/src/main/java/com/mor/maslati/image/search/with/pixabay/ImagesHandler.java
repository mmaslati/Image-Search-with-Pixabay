package com.mor.maslati.image.search.with.pixabay;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONObject;
import java.util.ArrayList;

public class ImagesHandler {

    // Variables:
    private Context context;
    private RecyclerView recyclerView;
    private Network network;

    private String currentQuery;
    private int    currentPageNum;


    //*******   Singleton   *******
    private static ImagesHandler INSTANCE = null;

    private ImagesHandler(Context context, RecyclerView recyclerView) {
        this.recyclerView   = recyclerView;
        this.context        = context;
        this.network        = new Network(context, this);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ImageGridRecyclerViewAdapter adapter = new ImageGridRecyclerViewAdapter(context, imagesResults);
        //adapter.setClickListener((ImageGridRecyclerViewAdapter.ItemClickListener)context);
        recyclerView.setAdapter(adapter);
    }


}
