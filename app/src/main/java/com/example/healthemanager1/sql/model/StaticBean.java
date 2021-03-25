package com.example.healthemanager1.sql.model;

public class StaticBean {

    public  String User_StaticRate,StaticRate_time;
    public StaticBean(String User_StaticRate, String StaticRate_time) {
        super();
        this.User_StaticRate = User_StaticRate;
        this.StaticRate_time=StaticRate_time;

    }
    public String toString(){
        return "UserBean [" +
                "User_StaticRate= " + User_StaticRate +
                ",StaticRate_time= " + StaticRate_time +

                "]";
    }
}
