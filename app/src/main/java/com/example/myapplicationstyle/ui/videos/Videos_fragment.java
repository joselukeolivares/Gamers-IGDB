package com.example.myapplicationstyle.ui.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.example.myapplicationstyle.DataBase.VideoEntry;
import com.example.myapplicationstyle.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Videos_fragment extends Fragment  implements Videos_Adapter.onClickVideosAdapter{
    private ArrayList<VideoEntry> videosArrayList=new ArrayList<>();


    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private String videosJsonArray;

    //game reference
    private ImageView cover;
    private TextView name;

    //Adapter
    private RecyclerView videos_recyclerView;
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
        videos_recyclerView =(RecyclerView)rootView.findViewById(R.id.screnshot_recycler_view);


        videos_adapter=new Videos_Adapter(this);
        layout= new GridLayoutManager(inflater.getContext(),1);
        videos_recyclerView.setLayoutManager(layout);
        videos_recyclerView.setAdapter(videos_adapter);


        if(videosJsonArray !=null && !videosJsonArray.isEmpty()){
            //build_ScreenshootList(screenshotJsonArray);
            name.setText(game_name);
            String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
            Picasso.with(getContext()).load(uri).into(cover);

            videos_jsonArray_toVideosList(videosJsonArray);
        }


        return rootView;
    }

    public void setData(String game_name,String game_cover,String videosJsonArray) {
        this.game_name=game_name;
        this.game_cover=game_cover;
        this.videosJsonArray=(videosJsonArray);
    }

    private void videos_jsonArray_toVideosList(String videosJsonArray) {

        try{
            JSONArray videosJsonArrayList = new JSONArray(videosJsonArray);
            Log.i(this.getClass().getName(),videosJsonArray);

            for(int j=0;j<videosJsonArrayList.length();j++){
                Log.i(this.getClass().getName(),j+"");
                JSONObject videoJsonObj=videosJsonArrayList.getJSONObject(j);

                String video_id=videoJsonObj.getString("video_id");
                String name=videoJsonObj.getString("name");
                int id=videoJsonObj.getInt("id");
                if(video_id!=null&&!video_id.isEmpty()){
                    VideoEntry videoEntry=new VideoEntry();
                    videoEntry.setName(name);
                    videoEntry.setId_IGDB(id);
                    videoEntry.setVideo_id(video_id);
                    videosArrayList.add(videoEntry);
                }

            }

            if(videosArrayList.size()>0){
                videos_adapter.setData(videosArrayList);
                videos_adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onClickVideo(String video_id) {
        Log.i(this.getClass().getName(),"vnd.youtube:"+video_id);
        Uri youtube=Uri.parse(getString(R.string.youtube_uri)+video_id);
        Intent intent=new Intent(Intent.ACTION_VIEW,youtube);
        startActivity(intent);
    }
}
