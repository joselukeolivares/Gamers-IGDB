package com.example.myapplicationstyle.ui.screenshots;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.ScreenshotEntry;
import com.example.myapplicationstyle.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class screenshot_adapter extends RecyclerView.Adapter<screenshot_adapter.vh> {
    private Context context;
    private ArrayList<ScreenshotEntry> images=new ArrayList<>();
    private clickonScreenshotAdapter clickonScreenshotAdapterObj;

    public interface clickonScreenshotAdapter{
         void onclickscreenshot(ScreenshotEntry image, boolean favorite, int position);
    }

    public screenshot_adapter(clickonScreenshotAdapter clickonScreenshotAdapterObj){
        this.clickonScreenshotAdapterObj=clickonScreenshotAdapterObj;
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout=R.layout.screenshot_view_holder;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(layout,parent,false);

        return new vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        ScreenshotEntry screenshotEntry=images.get(position);
        String uri=context.getString(R.string.uriCover)+screenshotEntry.getUrl()+context.getString(R.string.jpg);
        Log.i("screenshot uri",uri);
        Picasso.with(context).load(uri).into(holder.screenshot);

        if(!screenshotEntry.isFavorite()){
            holder.heart_icon.setVisibility(View.INVISIBLE);
            Log.i(this.getClass().getName(),"Visible");

            screenshotEntry.setFavorite(false);
        }else{
            holder.heart_icon.setVisibility(View.VISIBLE);
            Log.i(this.getClass().getName(),"inVisible");

            screenshotEntry.setFavorite(true);
        }

    }

    @Override
    public int getItemCount() {
        if(images!=null){
            return images.size();
        }else{
            return 0;
        }

    }

    public void setData(ArrayList<ScreenshotEntry> new_data){
        images=new_data;
    }

    public class vh extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView screenshot;
        TextView screen_number;
        ImageView heart_icon;

        public vh(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            screenshot=(ImageView)itemView.findViewById(R.id.screenshot_image_view_holder);
            screen_number=(TextView)itemView.findViewById(R.id.numer_screenshot_view_holder);
            heart_icon=(ImageView)itemView.findViewById(R.id.favorite_icon_viewHolder);

        }

        @Override
        public void onClick(View v) {
            ScreenshotEntry screenshotEntry=images.get(getAdapterPosition());
            boolean favorite;

            if(heart_icon.getVisibility()==View.VISIBLE){
                heart_icon.setVisibility(View.INVISIBLE);
                Log.i(this.getClass().getName(),"Visible");
                favorite=false;
                screenshotEntry.setFavorite(false);
            }else{
                heart_icon.setVisibility(View.VISIBLE);
                Log.i(this.getClass().getName(),"inVisible");
                favorite=true;
                screenshotEntry.setFavorite(true);
            }
            clickonScreenshotAdapterObj.onclickscreenshot(screenshotEntry,favorite,getAdapterPosition());


        }
    }
}
