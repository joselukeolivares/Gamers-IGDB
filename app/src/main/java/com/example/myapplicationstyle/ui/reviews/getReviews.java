package com.example.myapplicationstyle.ui.reviews;

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
import com.example.myapplicationstyle.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getReviews{

    String body_request;
    public getReviews(Context context, @Nullable AttributeSet attrs) {

    }

    public static  void getData(Context context, int categogy, final int game_idIGDB, final Review.VolleyCallBack successMethod){
        ///Log.i(this.getClass().getName(),"requesting");
        //String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        String url ="https://api-v3.igdb.com/private/reviews";

        RequestQueue mRequestQueue= Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "69351");

        } catch (JSONException e) {
            e.printStackTrace();
        }




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
                String httpPostBody="fields category,checksum,conclusion,content,created_at,game,introduction,likes,negative_points,platform,positive_points,slug,title,updated_at,url,user,user_rating,video,views;limit 50;where game="+game_idIGDB+";";
                    /*
                    String where_condition="where cover.image_id!=\"\" & cover.image_id!=null ";
                    if(!desc){
                        httpPostBody=httpPostBody.concat("sort popularity desc;");
                    }else{
                        httpPostBody=httpPostBody.concat("sort popularity asc;");
                    }




                    if(!search_request.isEmpty()){
                        where_condition=where_condition.concat(" & name ~ *\""+search_request+"\"*");
                    }

                    if(!platform.equals("9999")){
                        where_condition=where_condition.concat(" & platforms !=n & platforms = {"+platform+"}");
                    }
                    where_condition=where_condition.concat(";");
                    httpPostBody=httpPostBody.concat(where_condition);

                     */

                //Log.i(this.getClass().getName(),httpPostBody);
                return httpPostBody.getBytes();
            }
        };

        // Access the RequestQueue through your singleton class.
        //requestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
        mRequestQueue.add(jsonObjectRequest);

    }

}

