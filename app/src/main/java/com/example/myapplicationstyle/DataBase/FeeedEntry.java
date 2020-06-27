package com.example.myapplicationstyle.DataBase;

public class FeeedEntry {

    int id;
    int id_IGDB;
    int category;
    String games_related;
    String title;
    String video_youtubeId;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    long createdAt;

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getGames_related() {
        return games_related;
    }

    public void setGames_related(String games_related) {
        this.games_related = games_related;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_youtubeId() {
        return video_youtubeId;
    }

    public void setVideo_youtubeId(String video_youtubeId) {
        this.video_youtubeId = video_youtubeId;
    }


}
