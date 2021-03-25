package com.example.healthemanager1.sql.model;

public class MaxRateBean {

    public  String User_MaxRate,MaxRate_time;



    public MaxRateBean( String user_MaxRate, String maxRate_time) {
        super();
        this.User_MaxRate = user_MaxRate;

        this.MaxRate_time = maxRate_time;
    }


    public String toString(){
        return "UserBean [" +
                "User_MaxRate= " + User_MaxRate +
                ",MaxRate_time= " + MaxRate_time +

                "]";
    }
}
