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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_IGDB() {
        return id_IGDB;
    }

    public void setId_IGDB(int id_IGDB) {
        this.id_IGDB = id_IGDB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    private String video_id;
    private int id_game;
}
