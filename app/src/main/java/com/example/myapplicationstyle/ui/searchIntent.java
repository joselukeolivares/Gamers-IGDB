package com.example.myapplicationstyle.ui;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplicationstyle.MainActivity;
import com.example.myapplicationstyle.getJson;

import org.json.JSONArray;

public class searchIntent extends IntentService {
    public final static String BUNDLED_LISTENER = "listener";
    public searchIntent() {
        super("name");
        Log.i(this.getClass().getName(),"Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
         String platform_request=intent.getStringExtra("platform_request");
         String category_request=intent.getStringExtra("category_request");
          boolean desc=intent.getBooleanExtra("desc",true);
          String search_request=intent.getStringExtra("search_request");
        Log.i(this.getClass().getName(),"onHandleIntent");

        Bundle bundle=new Bundle();
        bundle.putString("search_request","jose luis olivares");
        ResultReceiver receiver = intent.getParcelableExtra(MyResultReceiver.class.getSimpleName());
        receiver.send(0,bundle);
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
