package com.example.myapplicationstyle;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class GameAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            String gameName,String gameCover,int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.game_app_widget);
        views.setTextViewText(R.id.appwidget_text, gameName);

        //String uri=context.getString(R.string.uriCover)+gameCover+context.getString(R.string.jpg);
        Log.i("widget name",gameName);
        Log.i("widget",gameCover);
        int coverId=views.getLayoutId();
        try{

            Bitmap b = Picasso.with(context).load(gameCover).get();
            views.setImageViewBitmap(R.id.game_cover_widget, b);

        }catch (IOException e){
            e.printStackTrace();
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateListRecipe(Context context, AppWidgetManager appWidgetManagerObj, String gameName, String gameCover, int[] appWidgetsId) {
        for(int appWidgetId:appWidgetsId){
            updateAppWidget(context, appWidgetManagerObj,gameName,gameCover, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager,gameName, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

