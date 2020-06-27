package com.example.myapplicationstyle.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.GameEntry;

import java.util.List;

public class GamesViewModel  extends AndroidViewModel{
    private LiveData<List<GameEntry>> games;

    public GamesViewModel(@NonNull Application application) {
        super(application);
        AppDataBase dataBase=AppDataBase.getInstance(this.getApplication());
        games=dataBase.gameDao().loadAllGames();
    }

    public LiveData<List<GameEntry>> getGames(){return  games;}

}
