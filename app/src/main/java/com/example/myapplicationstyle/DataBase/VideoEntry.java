package com.example.myapplicationstyle.DataBase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "video",foreignKeys = @ForeignKey(
        entity = GameEntry.class,
        parentColumns = "id",
        childColumns = "id_game",
        onDelete = CASCADE
))
public class VideoEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_IGDB;


    private String name;
    private String video_id;
    private int id_game;
}
