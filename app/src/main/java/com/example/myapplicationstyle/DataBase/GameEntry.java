package com.example.myapplicationstyle.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game")
public class GameEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_IGDB;

    private String Name;
    private String coverUrl;
    private String platforms;
    private double rating;
    private double total_rating;
    private int rating_count;
    private int total_rating_count;
    private String genres;
    private String summary;
    private int hypes;
    private int first_release_date;

    public String getInvolved_companies() {
        return involved_companies;
    }

    public void setInvolved_companies(String involved_companies) {
        this.involved_companies = involved_companies;
    }

    private String involved_companies;

    private String videos_id;
    private String screenshot_url;

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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(double total_rating) {
        this.total_rating = total_rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public int getTotal_rating_count() {
        return total_rating_count;
    }

    public void setTotal_rating_count(int total_rating_count) {
        this.total_rating_count = total_rating_count;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getHypes() {
        return hypes;
    }

    public void setHypes(int hypes) {
        this.hypes = hypes;
    }

    public int getFirst_release_date() {
        return first_release_date;
    }

    public void setFirst_release_date(int first_release_date) {
        this.first_release_date = first_release_date;
    }

    public String getVideos_id() {
        return videos_id;
    }

    public void setVideos_id(String videos_id) {
        this.videos_id = videos_id;
    }

    public String getScreenshot_url() {
        return screenshot_url;
    }

    public void setScreenshot_url(String screenshot_url) {
        this.screenshot_url = screenshot_url;
    }





}
