package com.example.myapplicationstyle.ui.Feeds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.reviews.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getFeeds {
    String body_request;

    public getFeeds(Context context, @Nullable AttributeSet attrs) {

    }

    Context context;

    public static  void getData(Context context, final String category, final int game_idIGDB, final Review.VolleyCallBack successMethod){
        ///Log.i(this.getClass().getName(),"requesting");
        //String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        String url ="https://api-v3.igdb.com/feeds";
        context=context;


        RequestQueue mRequestQueue= Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "69351");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Context finalContext = context;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(final JSONArray response) {
                        //textView.setText("Response: " + response.toString());
                        //Log.i(this.getClass().getName(),response.toString());
                        successMethod.succesVolley(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("Error","");
                        error.fillInStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("grant_type", "password");
                // volley will escape this for you
                //params.put("randomFieldFilledWithAwkwardCharacters", "{{%stuffToBe Escaped/");
                //params.put("username", "Alice");
                //params.put("id", "69359");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("user-key", "bd55a4c87ab2d1bc57f5486063ad75d3");

                return params;
            }

            @Override
            public byte[] getBody()  {
                String httpPostBody="fields title,category,checksum,content,created_at,feed_likes_count,feed_video,games.name,meta,published_at,pulse,slug,title,uid,updated_at,url,user;\n" +
                        "sort created_at desc; where content ~ \"https://www.youtube.com\"* ";

                    String where_condition="& category=";
                    if(category.equals(finalContext.getString(R.string.pref_news_label_value))){
                        where_condition=where_condition.concat("1;");
                    }else if(category.equals(finalContext.getString(R.string.pref_news_coming_value))){
                        where_condition=where_condition.concat("2;");

                    }else if(category.equals(finalContext.getString(R.string.pref_news_newTrailer_value))){
                        where_condition=where_condition.concat("3;");

                    }else if(category.equals(finalContext.getString(R.string.pref_news_uContributions_value))){
                        where_condition=where_condition.concat("4;");

                    }else if(category.equals(finalContext.getString(R.string.pref_news_uContribution_value))){
                        where_condition=where_condition.concat("5;");

                    }else if(category.equals(finalContext.getString(R.string.pref_news_pContribution_value))){
                        where_condition=where_condition.concat("6;");

                    }else{
                        where_condition=";";
                    }

                    httpPostBody=httpPostBody.concat(where_condition);

                    /*
                    <string name="pref_news_coming_value">coming</string>
                    <string name="pref_news_newTrailer_value">newTrailer</string>
                    <string name="pref_news_uContributions_value">uContributions</string>
                    <string name="pref_news_uContribution_value">uContribution</string>
                    <string name="pref_news_label_pContribution_item">page contribution item</string>
                    <string name="pref_news_pContribution_value">pContribution</string>
                    <string name="pref_news_all_value">all</string>

    */






                Log.i(this.getClass().getName(),httpPostBody);
                return httpPostBody.getBytes();
            }
        };

        // Access the RequestQueue through your singleton class.
        //requestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
        mRequestQueue.add(jsonObjectRequest);

    }

}
