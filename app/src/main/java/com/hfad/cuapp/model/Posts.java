package com.hfad.cuapp.model;

public class Posts {
    String updatetopic;
    String update;
    String u_name;
    String post_time;
    String post_date;



    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public Posts(){

    }

    public Posts(String updatetopic, String update , String u_name,String update_time) {
        this.updatetopic = updatetopic;
        this.update = update;
        this.u_name=u_name;
        this.post_time=update_time;
    }

    public String getupdatetopic() {
        return updatetopic;
    }

    public String getupdate() {
        return update;
    }
    public void setupdatetopic (String updatetopic) {
        this.updatetopic = updatetopic;
    }

    public void setUpdate (String update) {
        this.update = update;}
}
