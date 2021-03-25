package com.example.healthemanager1.application;

import android.content.Context;

public class User1 {
    private Context mcontext;
    private String name;
    public User1(Context context){
        mcontext=context;
//        this.name=name;
    }

    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    };


}
