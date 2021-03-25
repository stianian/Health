package com.example.healthemanager1.sql.model;

public class WeightBean {

    public  String user_weight,weight_time;

    public WeightBean(String user_weight, String weight_time) {
        super();
        this.user_weight = user_weight;
        this.weight_time=weight_time;

    }

    public String toString(){
        return "UserBean [" +
                "user_weight= " + user_weight +
                ",weight_time= " + weight_time +

                "]";
    }

}
