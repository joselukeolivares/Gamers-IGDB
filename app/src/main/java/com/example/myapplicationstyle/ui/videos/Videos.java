package com.example.myapplicationstyle.ui.videos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplicationstyle.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Videos extends AppCompatActivity {

    FragmentManager fragmentManager=getSupportFragmentManager();
    Videos_fragment videos_fragment=new Videos_fragment();

    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private JSONObject game_jsonObj;
    private String videosJsonArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_layout);

        Intent intent=getIntent();
        if(savedInstanceState!=null){
            game_idIGDB=savedInstanceState.getInt("game_idIGDB");
            game_cover=savedInstanceState.getString("game_cover");
            game_name=savedInstanceState.getString("name");
            String game_string=savedInstanceState.getString("gameJsonObj");
            try {
                game_jsonObj=new JSONObject(game_string);
                Log.i(this.getClass().getName(),game_string);
                videosJsonArray =game_jsonObj.getJSONArray("videos").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }else if(intent!=null){
            if(intent.getIntExtra("game_idIGDB",0)!=0){

                game_idIGDB=intent.getIntExtra("game_idIGDB",0);
                game_cover=intent.getStringExtra("game_cover");
                game_name=intent.getStringExtra("name");
                String game_string=intent.getStringExtra("gameJsonObj");
                try {
                    game_jsonObj=new JSONObject(game_string);
                    Log.i(this.getClass().getName(),game_string);
                    videosJsonArray =game_jsonObj.getJSONArray("videos").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



        }

        if(videosJsonArray !=null && !videosJsonArray.isEmpty()){

            admin_videos_fragment();
        }else{
            Log.i(this.getClass().getName(),"screenshotJsonArray is null");
        }
    }

    public  void admin_videos_fragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.videos_container,videos_fragment)
                .commit();

        videos_fragment.setData(game_name,game_cover,videosJsonArray);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("game_idIGDB",game_idIGDB);
        outState.putString("game_cover",game_cover);
        outState.putString("name",game_name);
        outState.putString("gameJsonObj",game_jsonObj.toString());

    }
}
