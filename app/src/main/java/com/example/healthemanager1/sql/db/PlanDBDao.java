package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;


import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.PlanBean;

import java.util.ArrayList;
import java.util.List;


public class PlanDBDao {


    /** 数据表名称 */
    public static final String Table_NAME = "project";

    /** 表的字段名 */
    public static String USER_NAME= "user_name";
    public static String ID = "_id";

    public static String FOOD = "food";

    public static String EXERCISE_TIME = "food_time";

    public static String EXERCISE = "exercise";




    public static String SLEEP = "sleep";
    public static String SLEEP_TIME = "sleep_time";
    public static String RANGE_TIME = "ex_time";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;


    public PlanDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }


    public void addSleep (PlanBean planBean, String name, String time){
        ContentValues values =new ContentValues();
        values.put(USER_NAME,name);//姓名
        values.put(SLEEP,planBean.sleep);//日期
        values.put(SLEEP_TIME,planBean.sleep_time);//时间段
        values.put(RANGE_TIME,time);//时长
        mDatabase.insert(Table_NAME,null,values);
    }



    public void addStep(String step,String data){
        ContentValues values = new ContentValues();

        values.put(EXERCISE_TIME,data);
        values.put(EXERCISE,step);
        mDatabase.insert(Table_NAME,null,values);
    }





    public String getvalue(String data) {

        String myFloats=null;
        Cursor mCursor = mDatabase.rawQuery("select * from project where food_time = ? order by _id desc limit 1 ", new String[]{data});
        int ColumnIndex = mCursor.getColumnIndex(EXERCISE);
        while (mCursor.moveToNext()) {
            myFloats = mCursor.getString(ColumnIndex);
            mCursor.moveToNext();

        }
        mCursor.close();
        return myFloats;
    }

    public String[] getvalue1(String data,String name,int i){


        Cursor mCursor = mDatabase.rawQuery("select * from project where user_name = ? and sleep=? order by _id desc limit 1 ", new String[]{name,data} );
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex  =0;
        switch (i){
            case 0:
                ColumnIndex = mCursor.getColumnIndex(SLEEP_TIME);
                break;
            case 1:
                ColumnIndex= mCursor.getColumnIndex(RANGE_TIME);
                break;
            default:
                break;
        }

        while(mCursor.moveToNext()){// cursor.moveToNext() 向下移动一行,如果有内容，返回true

            for (int j = 0; j < mCursor.getCount(); j++)
            {
                myFloats[j] = mCursor.getString(ColumnIndex);
                mCursor.moveToNext();
            }

        }
        mCursor.close();
        return myFloats;
    }



    public String[] getvalue2(String name,int i) {


        Cursor mCursor = mDatabase.rawQuery("select * from project where user_name = ? ", new String[]{name});
        int ColumnIndex =0;
        String[] myFloats = new String[mCursor.getCount()];
        switch (i){
            case 0:
                ColumnIndex = mCursor.getColumnIndex(RANGE_TIME);
                break;
            case 1:
                ColumnIndex= mCursor.getColumnIndex(SLEEP);
                break;
            case 2:
                ColumnIndex= mCursor.getColumnIndex(SLEEP_TIME);
                break;
            default:
                break;
        }

        while (mCursor.moveToNext()) {
            for (int j = 0; j < mCursor.getCount(); j++)
            {
                myFloats[j] = mCursor.getString(ColumnIndex);
                mCursor.moveToNext();
            }

        }
        mCursor.close();
        return myFloats;
    }





    public List<PlanBean> getPlanByPage(String name,String data){

        List<PlanBean> alldata = new ArrayList<PlanBean>();
        Cursor cursor = mDatabase.rawQuery("select * from project where user_name = ? and sleep = ? order by _id desc limit 1", new String[]{name,data} );

        while(cursor.moveToNext()){

            String user_height = cursor.getString(cursor.getColumnIndex(SLEEP_TIME));//时间段
            String height_time=cursor.getString(cursor.getColumnIndex(RANGE_TIME));//时长
            PlanBean planBean = new PlanBean(user_height,height_time);
            alldata.add(planBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(1000);// 休眠2秒比较耗时的情况
        return alldata;

    }


    public int getSlCount(){

        Cursor cursor = mDatabase.query(Table_NAME, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);// 仅查了一列，count(*) 这一刻列

        cursor.close();

        return count;

    }



}
