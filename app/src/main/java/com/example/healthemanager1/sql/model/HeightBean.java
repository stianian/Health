package com.example.healthemanager1.sql.model;

public class HeightBean {


    public  String user_height,height_time;


    public HeightBean(String user_height, String height_time) {
        super();
        this.user_height = user_height;
        this.height_time=height_time;

    }

    public String toString(){
        return "UserBean [" +
                "user_height= " + user_height +
                ",height_time= " + height_time +

                "]";
    }
}
