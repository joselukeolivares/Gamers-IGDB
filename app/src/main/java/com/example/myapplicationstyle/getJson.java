package com.example.myapplicationstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class getJson extends View{


    public getJson(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static void getData(Context context, final MainActivity.VolleyCallBack successMethod){
        ///Log.i(this.getClass().getName(),"requesting");
        //String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        String url ="https://api-v3.igdb.com/games";
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
                String httpPostBody="sort popularity desc;where id=69351;";
                // usually you'd have a field with some values you'd want to escape, you need to do it yourself if overriding getBody. here's how you do it
                /*
                try {
                    httpPostBody=httpPostBody+ URLEncoder.encode("{{%stuffToBe Escaped/","UTF-8");
                } catch (UnsupportedEncodingException exception) {
                    Log.e("ERROR", "exception", exception);
                    // return null and don't pass any POST string if you encounter encoding error
                    return null;
                }

                 */
                return httpPostBody.getBytes();
            }
        };

        // Access the RequestQueue through your singleton class.
        //requestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
        mRequestQueue.add(jsonObjectRequest);

     }

    }
