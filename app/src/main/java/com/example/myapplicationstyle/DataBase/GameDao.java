package com.example.myapplicationstyle.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    LiveData<List<GameEntry>> loadAllGames();

    @Insert
    long insertGame(GameEntry gameEntry);

    @Delete
    void deleteGame(GameEntry gameEntry);

    @Query("DELETE FROM game")
    void deleteAllGames();

    @Query("DELETE FROM game where id=:id")
    void deleteGameById(int id);

    @Query("SELECT * FROM game WHERE id=:id")
    GameEntry loadGameById(int id);
}
