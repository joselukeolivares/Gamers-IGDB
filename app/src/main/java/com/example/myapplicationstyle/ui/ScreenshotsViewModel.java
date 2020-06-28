package com.example.myapplicationstyle.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.ScreenshotEntry;

import java.util.List;

public class ScreenshotsViewModel extends AndroidViewModel {

    LiveData<List<ScreenshotEntry>> screenshots;

    public ScreenshotsViewModel(@NonNull Application application) {
        super(application);
        AppDataBase appDataBase=AppDataBase.getInstance(this.getApplication());
        this.screenshots=appDataBase.screenshotDAO().loadAllScreenshots();
    }

    public LiveData<List<ScreenshotEntry>> getScreenshots(){return  screenshots;}
}
