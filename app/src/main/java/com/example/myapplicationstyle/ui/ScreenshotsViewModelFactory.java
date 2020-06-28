package com.example.myapplicationstyle.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplicationstyle.DataBase.AppDataBase;

public class ScreenshotsViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final AppDataBase appDataBase;
    private final int gameId;

    public ScreenshotsViewModelFactory(AppDataBase appDataBase, int gameId) {
        this.appDataBase = appDataBase;
        this.gameId = gameId;
    }

    public <T extends ViewModel> T  create(Class<T> modelClass){
        return (T) new ScreenShootViewModel(appDataBase,gameId);
    }
}
