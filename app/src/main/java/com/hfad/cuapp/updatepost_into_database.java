package com.hfad.cuapp;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

public class updatepost_into_database {
    String updatetopic;
    String update;
    String u_name;

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(String disLikes) {
        this.disLikes = disLikes;
    }

    String Likes;
    String disLikes;
    //String post_date;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    String batch;

    String post_time;
    String uid ;



    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public updatepost_into_database() {
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getUpdatetopic() {
        return updatetopic;
    }

    public void setUpdatetopic(String updatetopic) {
        this.updatetopic = updatetopic;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
