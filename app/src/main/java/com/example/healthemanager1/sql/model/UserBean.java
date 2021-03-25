package com.example.healthemanager1.sql.model;

public class UserBean {
    public  String username;
    public String user_id;
     public String       user_sex;
     public String       user_age;
       public String      user_data;

    public UserBean(String name, String sex, String age, String born_data) {
        super();
        this.username = name;

        this.user_sex=sex;
        this.user_age=age;
        this.user_data=born_data;
    }


    @Override
    public String toString() {

        return "BlackNumBean [name= " + username + ", " +
                "sex= " + user_sex + "," +
                "age="+ user_age+","+
                "born_data= "+ user_data+"]";
    }
}
