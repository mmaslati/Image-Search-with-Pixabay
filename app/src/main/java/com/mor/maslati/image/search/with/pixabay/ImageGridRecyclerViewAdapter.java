package com.mor.maslati.image.search.with.pixabay;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class ImageGridRecyclerViewAdapter extends RecyclerView.Adapter<ImageGridRecyclerViewAdapter.ViewHolder> {

    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    private int screenWidth;
    private int screenHeight;


    // data is passed into the constructor
    ImageGridRecyclerViewAdapter(Context context, List<JSONObject> data) {
        this.mInflater    = LayoutInflater.from(context);
        this.mData        = data;
        this.context      = context;
        this.screenWidth  = getScreenSize()[0];
        this.screenHeight = getScreenSize()[1];
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_images_row, parent, false);
        return new ViewHolder(view);
    }

    public int[] getScreenSize(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int[] paramsArray = { width, height};

        return paramsArray;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        JSONObject imageObject = mData.get(position);

        try {

            double currentImageHeight = imageObject.getInt("previewHeight");
            double currentImageWidth = imageObject.getInt("previewWidth");
            double imageRatio = currentImageWidth/currentImageHeight;

            double newWidthDouble = screenHeight/7*imageRatio;
            int newWidth = (int) newWidthDouble;

            Picasso.get()
                    .load(imageObject.getString("previewURL"))
                    .resize(newWidth, screenHeight/7)
                    //.centerInside()
                    .into(holder.imageLeft);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rowLayout;
        ImageView imageLeft;

        ViewHolder(View itemView) {
            super(itemView);
            rowLayout       = itemView.findViewById(R.id.row);
            imageLeft  = itemView.findViewById(R.id.imageLeft);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    JSONObject getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}