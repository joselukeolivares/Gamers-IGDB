package com.example.myapplicationstyle.ui.screenshots;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;

import com.example.myapplicationstyle.DataBase.ScreenshotEntry;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.game_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Screenshots extends AppCompatActivity {

    private FragmentManager fragmentManager=getSupportFragmentManager();
    private Screenshot_fragment screenshot_fragment=new Screenshot_fragment();

    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private int game_id;
    private JSONObject game_jsonObj;
    private String screenshotJsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.screenshots);

        ActionBar actionBar=this.getSupportActionBar();

        if(actionBar!=null){
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
        }

        Intent intent=getIntent();
        if(savedInstanceState!=null){

            game_idIGDB=savedInstanceState.getInt("game_idIGDB",0);
            game_cover=savedInstanceState.getString("game_cover");
            game_name=savedInstanceState.getString("name");

            screenshotJsonArray=savedInstanceState.getString("screeshotJsonArray");

        }else if(intent!=null){

            if(intent.getIntExtra("game_idIGDB",0)!=0){

                game_idIGDB=intent.getIntExtra("game_idIGDB",0);
                game_cover=intent.getStringExtra("game_cover");
                game_name=intent.getStringExtra("name");
                String game_string=intent.getStringExtra("gameJsonObj");
                try {
                    game_jsonObj=new JSONObject(game_string);
                    Log.i(this.getClass().getName(),game_string);
                    screenshotJsonArray=game_jsonObj.getJSONArray("screenshots").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            if(screenshotJsonArray!=null && !screenshotJsonArray.isEmpty()){

                admin_screenshot_fragment();
            }else{
                Log.i(this.getClass().getName(),"screenshotJsonArray is null");
            }


        }else {

        }Log.i(this.getClass().getName(),"Intent is null");


    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.homeAsUp){
            Intent intent=new Intent(this, game_detail.class);
            //intent.putExtra("game_idIGDB",game_idIGDB);
            //intent.putExtra("js")
        }

        return true;
    }

     */

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("game_idIGDB",game_idIGDB);
        outState.putString("game_cover",game_cover);
        outState.putString("game_cover",game_cover);
        outState.putString("game_cover",game_cover);
    }

    public void admin_screenshot_fragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.screenshot_container,screenshot_fragment)
                .commit();
        screenshot_fragment.setData(game_name,game_id,game_cover,screenshotJsonArray);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
