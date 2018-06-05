package com.example.backfire.myapp.bean;

import android.app.backup.FileBackupHelper;

/**
 * Created by backfire on 2017/11/8.
 */

public class FilmBean {
    private String titles;
    private String url;
    private String imageUrl;

    public FilmBean(String titles,String url){
        this.titles = titles;
        this.url = url;

    }

    public FilmBean(String titles,String url,String imageUrl){
        this.titles = titles;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
