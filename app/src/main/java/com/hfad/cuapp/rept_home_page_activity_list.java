package com.hfad.cuapp;

import android.widget.ImageView;

public class rept_home_page_activity_list {
    private String content_name;
    int iv;

    public rept_home_page_activity_list(String content_name,int r) {
        this.content_name = content_name;
        iv = r;
    }

    public String getContent_name() {
        return content_name;
    }

    public int getIv() {
        return iv;
    }
}
