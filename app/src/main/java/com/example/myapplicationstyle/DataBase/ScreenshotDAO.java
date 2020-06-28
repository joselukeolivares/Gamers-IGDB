package com.example.myapplicationstyle.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ScreenshotDAO {
    @Query("SELECT * FROM screenshot")
    LiveData<List<ScreenshotEntry>> loadAllScreenshots();

    @Insert
    long insertScreenshot(ScreenshotEntry screenshotEntry);

    @Delete
    void deleteScreenshot(ScreenshotEntry screenshotEntry);

    @Query("DELETE FROM screenshot")
    void deleteAllScreenshots();

    @Query("DELETE FROM screenshot where id=:id")
    void deleteScreenshotById(int id);

    @Query("SELECT * FROM screenshot WHERE id_game=:id_game")
    List<ScreenshotEntry> loadScreenshotByGameId(int id_game);
}
