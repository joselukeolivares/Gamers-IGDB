package com.example.myapplicationstyle.DataBase;

public class ReviewEntry {
    int id;
    int id_IGDB;
    String category;
    String content;
    String introduction;
    int likes;

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    long created_at;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getNegative_points() {
        return negative_points;
    }

    public void setNegative_points(String negative_points) {
        this.negative_points = negative_points;
    }

    public String getPositive_points() {
        return positive_points;
    }

    public void setPositive_points(String positive_points) {
        this.positive_points = positive_points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    String negative_points;
    String positive_points;
    String title;
    String user;
    int views;
}
