package com.example.myapplicationstyle.DataBase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "screenshot")
public class ScreenshotEntry {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_IGB() {
        return id_IGB;
    }

    public void setId_IGB(int id_IGB) {
        this.id_IGB = id_IGB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_IGB;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    private boolean favorite=false;

    public int getGame_id_IGDB() {
        return game_id_IGDB;
    }

    public void setGame_id_IGDB(int game_id_IGDB) {
        this.game_id_IGDB = game_id_IGDB;
    }

    private int game_id_IGDB;

    private String name;
    private String url;
    private int id_game;

}
