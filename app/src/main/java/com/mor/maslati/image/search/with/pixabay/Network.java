package com.mor.maslati.image.search.with.pixabay;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class Network {

    private ImagesHandler imagesHandler;
    private Context context;
    private String key;
    private final String apiUrl = "https://pixabay.com/api/";

    Network(Context context, ImagesHandler imagesHandler) {

        this.context = context;
        this.key     = context.getString(R.string.pixabay );
        this.imagesHandler = imagesHandler;
    }

    // Helper For all API Calls
    private void sendIt(String urlString, Response.Listener successListener, Response.ErrorListener failedListener){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,successListener, failedListener );

        queue.add(stringRequest);
    }

    // Attempting to Parse successful response.
    private void parseImagesJSON(String fullImagesAsJSON){

        ArrayList<JSONObject> images = new ArrayList<>();

        JSONObject reader;

        try {
            reader = new JSONObject(fullImagesAsJSON);

            JSONArray hits = (JSONArray) reader.get("hits");

            for (int i = 0; i < hits.length(); i++) {

                JSONObject image = hits.getJSONObject(i);

                images.add(image);
            }

            imagesHandler.fillUpGrid(images);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    void getImagesByQueryString(String query, int pageNumber){

        Response.Listener successGettingImages = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseImagesJSON( response );

            }
        };

        Response.ErrorListener failedGettingImages = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error:",error.toString());
            }
        };


        query = query.replaceAll(" ","+");

        String getImagesWithQueryUrl = apiUrl+"?key="+key+"&q="+query+"&page="+pageNumber;

        Log.d("Mor",getImagesWithQueryUrl);

        sendIt(getImagesWithQueryUrl,successGettingImages,failedGettingImages);
    }

}
