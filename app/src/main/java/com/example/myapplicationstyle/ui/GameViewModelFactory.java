package com.example.myapplicationstyle.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplicationstyle.DataBase.AppDataBase;

public class GameViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDataBase appDataBase;
    private final int gameEntryId;

    public GameViewModelFactory(AppDataBase appDataBase, int gameEntryId) {
        this.appDataBase = appDataBase;
        this.gameEntryId = gameEntryId;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GameViewModel(appDataBase,gameEntryId);
    }

}
