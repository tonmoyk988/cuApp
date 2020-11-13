package com.hfad.cuapp.model;

import com.hfad.cuapp.R;
import com.hfad.cuapp.rept_home_page_activity_list;

import java.util.ArrayList;
import java.util.List;

public class dept_main_page {
    List<rept_home_page_activity_list> activity_lists;

    public dept_main_page(){

        activity_lists = new ArrayList<>();//int r= R.drawable.update;
        rept_home_page_activity_list item1 = new rept_home_page_activity_list("Class Updates", R.drawable.update);
        activity_lists.add(item1);
        rept_home_page_activity_list item2  = new rept_home_page_activity_list("Class Schedule",R.drawable.schedule);
        activity_lists.add(item2);
        rept_home_page_activity_list item3  = new rept_home_page_activity_list("Notice Board",R.drawable.notice_board);
        activity_lists.add(item3);
        rept_home_page_activity_list item4  = new rept_home_page_activity_list("Community",R.drawable.community);
        activity_lists.add(item4);
        rept_home_page_activity_list item5  = new rept_home_page_activity_list("Slides and Books",R.drawable.share);
        activity_lists.add(item5);
        rept_home_page_activity_list item6  = new rept_home_page_activity_list("Activities",R.drawable.activity);
        activity_lists.add(item6);
        rept_home_page_activity_list item7  = new rept_home_page_activity_list("Dept. info",R.drawable.info);
        activity_lists.add(item7);
        rept_home_page_activity_list item8  = new rept_home_page_activity_list("Gallery",R.drawable.gallery);
        activity_lists.add(item8);
    }
    public List<rept_home_page_activity_list> getlist(){
        return  activity_lists;
    }
}
