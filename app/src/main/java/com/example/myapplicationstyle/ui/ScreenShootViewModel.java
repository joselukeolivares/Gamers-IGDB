package com.example.myapplicationstyle.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.ScreenshotEntry;

import java.util.List;

public class ScreenShootViewModel extends ViewModel {
    private LiveData<List<ScreenshotEntry>> screenshots;

    public ScreenShootViewModel(AppDataBase appDataBase,int gameId){
        screenshots=appDataBase.screenshotDAO().loadScreenshotByGameId(gameId);
    }
}
