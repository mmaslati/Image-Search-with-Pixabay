package com.mor.maslati.image.search.with.pixabay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PageViewAdapter extends PagerAdapter {

    private ImagesHandler imagesHandler;
    private Context context;



    public PageViewAdapter(ImagesHandler imagesHandler) {

        this.context        = imagesHandler.context;
        this.imagesHandler  = imagesHandler;


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView       = mInflater.inflate(R.layout.full_screen_image, container, false);
        ImageView mImage    = itemView.findViewById(R.id.fullScreenImageView);

        JSONObject image = imagesHandler.imagesResults.get(position);

        try {
            Picasso.get()
                    .load(image.getString("largeImageURL"))
                    .into(mImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        container.addView(itemView);

        return itemView;

    }

    @Override
    public int getCount() {
        return imagesHandler.imagesResults.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;


    }
}
