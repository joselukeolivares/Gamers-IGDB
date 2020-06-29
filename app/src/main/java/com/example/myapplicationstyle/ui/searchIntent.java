package com.example.myapplicationstyle.ui;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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
import com.example.myapplicationstyle.MainActivity;
import com.example.myapplicationstyle.getJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class searchIntent extends IntentService {
    public final static String BUNDLED_LISTENER = "listener";
    public searchIntent() {
        super("name");
        Log.i(this.getClass().getName(),"Service");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
         String platform_request=intent.getStringExtra("platform_request");
         String category_request=intent.getStringExtra("category_request");
          boolean desc=intent.getBooleanExtra("desc",true);
          final String search_request=intent.getStringExtra("search_query");
        Log.i(this.getClass().getName(),"onHandleIntent"+search_request);

        final Bundle bundle=new Bundle();
        getJson.getData(this, platform_request, category_request, desc, search_request, new MainActivity.VolleyCallBack() {
            @Override
            public void succesVolley(JSONArray response) {
                bundle.putString("search_query",response.toString());
                ResultReceiver receiver = intent.getParcelableExtra(MyResultReceiver.class.getSimpleName());
                receiver.send(0,bundle);
            }
        });

    }
/*
    @Override
    protected void onHandleIntent(Intent intent){
        ResultReceiver receiver = intent.getParcelableExtra(MyResultReceiver.class.getSimpleName());

        boolean result = Boolean.TRUE;

        if(result){
            Bundle resultData = new Bundle();
            receiver.send(Activity.RESULT_OK, resultData);
        }else{
            receiver.send(Activity.RESULT_CANCELED, null);
        }
    }

 */

    public static class MyResultReceiver extends ResultReceiver {

        private OnMyResultListener mListener;

        public interface OnMyResultListener {
            public void onReceiveResult(int resultCode, Bundle resultData);
        }

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        public MyResultReceiver setOnMyResultListener(OnMyResultListener listener){
            mListener = listener;
            return this;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            if(mListener != null){
                mListener.onReceiveResult(resultCode, resultData);
            }
        }
    }



}
