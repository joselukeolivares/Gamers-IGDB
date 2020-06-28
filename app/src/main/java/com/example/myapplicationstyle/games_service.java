package com.example.myapplicationstyle;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class games_service extends IntentService {

    public games_service() {
        super("Gamers");

    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String gameName=intent.getStringExtra("game");
        String gameCover=intent.getStringExtra("coverGame");

        AppWidgetManager appWidgetManagerObj=AppWidgetManager.getInstance(this);


        Log.i("onHandleIntent",gameName);
        int[] appWidgetsId=appWidgetManagerObj.getAppWidgetIds(new ComponentName(this,GameAppWidget.class));
        GameAppWidget.updateListRecipe(this,appWidgetManagerObj,gameName,gameCover,appWidgetsId);

    }

    public static void updatingService(Context context, String gameName, String coverGame){

        Intent intent=new Intent(context,games_service.class);
        intent.putExtra("game",gameName);
        intent.putExtra("coverGame",coverGame);
        Log.i("updatingService",coverGame);
        intent.setAction("ACTIONMAN");
        context.startService(intent);

    }
}
