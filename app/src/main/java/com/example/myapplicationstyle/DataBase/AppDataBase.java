package com.example.myapplicationstyle.DataBase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {GameEntry.class,ScreenshotEntry.class},version = 1, exportSchema = false)


public abstract class AppDataBase extends RoomDatabase{

    private static final String LOG_TAG=AppDataBase.class.getName();
    private  static final Object LOCK=new Object();
    private static  final String DATABASE_NAME="movieBase";
    private static  AppDataBase sInstance;

    public static AppDataBase getInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.i(LOG_TAG,"Creating new database instance");
                sInstance= Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDataBase.class,
                        AppDataBase.DATABASE_NAME
                ).fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.i(AppDataBase.LOG_TAG,"Getting the database instance");
        return sInstance;
    }

    public abstract GameDao gameDao();

    public abstract  ScreenshotDAO screenshotDAO();


}
