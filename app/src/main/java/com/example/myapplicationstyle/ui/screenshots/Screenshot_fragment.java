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

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.ScreenshotEntry;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.AppExecutors;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Screenshot_fragment extends Fragment implements  screenshot_adapter.clickonScreenshotAdapter{
    //Screenshot data
    private ArrayList<ScreenshotEntry> image=new ArrayList<>();
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

    AppDataBase appDataBase;

    public Screenshot_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.screenshot_fragment,container,false);
        cover=(ImageView)rootView.findViewById(R.id.game_cover_screenshot);
        name=(TextView)rootView.findViewById(R.id.game_name_screenshot);
        screenshot_recyclerView=(RecyclerView)rootView.findViewById(R.id.screnshot_recycler_view);
        appDataBase=AppDataBase.getInstance(container.getContext());

        screenAdapter=new screenshot_adapter(this);
        layout= new GridLayoutManager(inflater.getContext(),2);
        screenshot_recyclerView.setLayoutManager(layout);
        screenshot_recyclerView.setAdapter(screenAdapter);


        if(screenshotJsonArray!=null && !screenshotJsonArray.isEmpty()){
            build_ScreenshootList(screenshotJsonArray);
            name.setText(game_name);
            String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
            Picasso.with(getContext()).load(uri).into(cover);

            findScreenshot_inDB();

        }


        return rootView;
    }

    private void findScreenshot_inDB() {
    }

    public void setData(String game_name, int game_id, String game_cover, String imageJsonArray) {
        this.game_name=game_name;
        this.game_cover=game_cover;
        this.screenshotJsonArray=imageJsonArray;
    }

    public void build_ScreenshootList(String images) {
        try {
            Log.i(this.getClass().getName(),images);
            JSONArray imagesList = new JSONArray(images);

            for(int j=0;j<imagesList.length();j++){
                ScreenshotEntry screenshotEntry=new ScreenshotEntry();
                JSONObject image_idObj=imagesList.getJSONObject(j);

                String image_id=image_idObj.optString("image_id");

                if(image_id!=null&&!image_id.isEmpty()){
                    screenshotEntry.setUrl(image_id);
                    screenshotEntry.setFavorite(false);

                    image.add(screenshotEntry);
                    Log.i(this.getClass().getName(),image_id);
                }else{
                    Log.i(this.getClass().getName(),"image_id is null or empty");
                }


            }

            if(image.size()>0){
                screenAdapter.setData(image);
                screenAdapter.notifyDataSetChanged();
                findImage();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onclickscreenshot(ScreenshotEntry image_fromAdapter,boolean favorite,int position) {
        Log.i(this.getClass().getName(),image_fromAdapter.getUrl());
        image.get(position).setFavorite(favorite);
        findImage();
        if(favorite){
            insertImage(position);
        }else{
            deleteImage(position);

        }
    }

    private void deleteImage(final int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int id=image.get(position).getId();

                appDataBase.screenshotDAO().deleteScreenshotById(id);
                Log.i(this.getClass().getName(),"Screenshot deleted with ID");
            }
        });
    }

    private void insertImage(final int position) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long idDB=appDataBase.screenshotDAO().insertScreenshot(image.get(position));
                image.get(position).setId((int)idDB);
                Log.i(this.getClass().getName(),"Screenshot inserted with ID"+idDB);

            }
        });
    }

    public void findImage(){
        //ScreenshotEntry screenshotEntry=image.get(position);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ScreenshotEntry> screenshotEntry_db=appDataBase.screenshotDAO().loadScreenshotByGameId(game_idIGDB);
                if(screenshotEntry_db!=null){
                    image.addAll(screenshotEntry_db);
                    /*
                    for(int k=0;k<screenshotEntry_db.size();k++){
                        ScreenshotEntry screenshotEntry_list=screenshotEntry_db.get(k);
                        if(screenshotEntry_list.getId()==)
                    }

                     */
                }
            }
        });

    }
}
