package com.example.healthemanager1.application;

import android.app.Application;

import com.example.healthemanager1.sql.DBMaster;
import com.facebook.stetho.Stetho;

/**
 * 姓名
 * 步数
 */

public class MyApplication extends Application {

    public static DBMaster mDBMaster;//数据库操作者实例
    public static User1 name;
    public static Step step;



    @Override
    public void onCreate() {
        super.onCreate();
        //启动数据库
        mDBMaster = new DBMaster(getApplicationContext());
        mDBMaster.openDataBase();
       name=new User1(getApplicationContext());
       step=new Step(getApplicationContext());
        Stetho.initializeWithDefaults(this);
    }
}
