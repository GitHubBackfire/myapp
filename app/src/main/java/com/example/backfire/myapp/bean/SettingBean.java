package com.example.backfire.myapp.bean;

/**
 * Created by backfire on 2017/12/12.
 */

public class SettingBean {
    private String title;
    private int iconResId;


    public SettingBean(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
