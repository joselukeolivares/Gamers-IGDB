package com.example.myapplicationstyle.ui.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.screenshots.screenshot_adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Videos_fragment extends Fragment  implements Videos_Adapter.onClickVideosAdapter{
    private ArrayList<String> videosList=new ArrayList<>();
    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private String screenshotJsonArray;

    //game reference
    private ImageView cover;
    private TextView name;

    //Adapter
    private RecyclerView screenshot_recyclerView;
    private Videos_Adapter videos_adapter;
    private GridLayoutManager layout;

    public Videos_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.screenshot_fragment,container,false);
        cover=(ImageView)rootView.findViewById(R.id.game_cover_screenshot);
        name=(TextView)rootView.findViewById(R.id.game_name_screenshot);
        screenshot_recyclerView=(RecyclerView)rootView.findViewById(R.id.screnshot_recycler_view);


        videos_adapter=new Videos_Adapter(this);
        layout= new GridLayoutManager(inflater.getContext(),2);
        screenshot_recyclerView.setLayoutManager(layout);
        screenshot_recyclerView.setAdapter(videos_adapter);


        if(screenshotJsonArray!=null && !screenshotJsonArray.isEmpty()){
            //build_ScreenshootList(screenshotJsonArray);
            name.setText(game_name);
            String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
            Picasso.with(getContext()).load(uri).into(cover);
        }


        return rootView;
    }

    public void setData(String videosJsonArray) {
        videos_jsonArray_toVideosList(videosJsonArray);
    }

    private void videos_jsonArray_toVideosList(String videosJsonArray) {

        try{
            JSONArray videosList = new JSONArray(videosJsonArray);
            for(int i=0;i<videosList.length();i++){
                JSONObject videoJsonObj=videosList.getJSONObject(i);

                String video_id=videoJsonObj.getString("video_id");
                if(video_id!=null&&!video_id.isEmpty()){
                    videosList.put(video_id);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onClickVideo(String video_id) {

    }
}
