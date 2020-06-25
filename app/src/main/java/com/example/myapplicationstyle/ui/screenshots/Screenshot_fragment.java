package com.example.myapplicationstyle.ui.screenshots;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Screenshot_fragment extends Fragment implements  screenshot_adapter.clickonScreenshotAdapter{
    //Screenshot data
    private ArrayList<String> image=new ArrayList<>();
    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private String screenshotJsonArray;

    //game reference
    private ImageView cover;
    private TextView name;

    //Adapter
    private RecyclerView screenshot_recyclerView;
    private screenshot_adapter screenAdapter;
    private GridLayoutManager layout;

    public Screenshot_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.screenshot_fragment,container,false);
        cover=(ImageView)rootView.findViewById(R.id.game_cover_screenshot);
        name=(TextView)rootView.findViewById(R.id.game_name_screenshot);
        screenshot_recyclerView=(RecyclerView)rootView.findViewById(R.id.screnshot_recycler_view);


        screenAdapter=new screenshot_adapter(this);
        layout= new GridLayoutManager(inflater.getContext(),2);
        screenshot_recyclerView.setLayoutManager(layout);
        screenshot_recyclerView.setAdapter(screenAdapter);


        if(screenshotJsonArray!=null && !screenshotJsonArray.isEmpty()){
            build_ScreenshootList(screenshotJsonArray);
            name.setText(game_name);
            String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
            Picasso.with(getContext()).load(uri).into(cover);
        }


        return rootView;
    }

    public void setData(String game_name, String game_cover, String imageJsonArray) {
        this.game_name=game_name;
        this.game_cover=game_cover;
        this.screenshotJsonArray=imageJsonArray;
    }

    public void build_ScreenshootList(String images) {
        try {
            Log.i(this.getClass().getName(),images);
            JSONArray imagesList = new JSONArray(images);

            for(int j=0;j<imagesList.length();j++){
                JSONObject image_idObj=imagesList.getJSONObject(j);

                String image_id=image_idObj.optString("image_id");

                if(image_id!=null&&!image_id.isEmpty()){
                    image.add(image_id);
                    Log.i(this.getClass().getName(),image_id);
                }else{
                    Log.i(this.getClass().getName(),"image_id is null or empty");
                }


            }

            if(image.size()>0){
                screenAdapter.setData(image);
                screenAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onclickscreenshot(String image,boolean favorite) {
        Log.i(this.getClass().getName(),image);
    }
}
