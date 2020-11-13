package com.hfad.cuapp.model;

public class new_asnmnt_ct {
    String subj,date,dtls,task_name;
    int serial_key;

    public new_asnmnt_ct(){};
    public new_asnmnt_ct(String f,String g,String h){
        subj=f;
        date=g;
        dtls=h;
    }

    public int getSerial_key() {
        return serial_key;
    }

    public void setSerial_key(int serial_key) {
        this.serial_key = serial_key;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDtls() {
        return dtls;
    }

    public void setDtls(String dtls) {
        this.dtls = dtls;
    }
}
