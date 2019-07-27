package com.hfad.cuapp;

public class Posts {
    String updatetopic,update,u_name;

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public Posts(){

    }

    public Posts(String updatetopic, String update ,String u_name) {
        this.updatetopic = updatetopic;
        this.update = update;
        this.u_name=u_name;
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
