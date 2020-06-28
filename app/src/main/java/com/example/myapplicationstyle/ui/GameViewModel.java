package com.example.myapplicationstyle.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.GameEntry;

public class GameViewModel extends ViewModel {
    private GameEntry game;


    public GameViewModel(AppDataBase dataBase,int gameEntryID) {

        game=dataBase.gameDao().loadGameById(gameEntryID);
    }

    public GameEntry getGame(){return  game;}

}
