package com.example.myapplicationstyle.DataBase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "screenshot",foreignKeys = @ForeignKey(
        entity = GameEntry.class,
        parentColumns = "id",
        childColumns = "id_game",
        onDelete = CASCADE
))
public class ScreenshotEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_IGB;

    private String name;
    private String url;
    private int id_game;

}
