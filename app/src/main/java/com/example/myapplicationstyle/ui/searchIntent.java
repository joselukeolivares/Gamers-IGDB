package com.example.myapplicationstyle.ui;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplicationstyle.MainActivity;
import com.example.myapplicationstyle.getJson;

import org.json.JSONArray;

public class searchIntent extends IntentService {

    public searchIntent() {
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
         String platform_request=intent.getStringExtra("platform_request");
         String category_request=intent.getStringExtra("category_request");
          boolean desc=intent.getBooleanExtra("desc",true);
          String search_request=intent.getStringExtra("search_request");

        Log.i(this.getClass().getName(),"Service");
        getJson.getData(this,platform_request,category_request,desc,search_request, new MainActivity.VolleyCallBack() {


            @Override
            public void succesVolley(JSONArray response) {

                Log.i(this.getClass().getName(), response.toString());
                //Intent intent1=new Intent(this,games_host.class);

            }
        });

    }
}
