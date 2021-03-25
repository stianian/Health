package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.healthemanager1.sql.DBConfig;
import com.example.healthemanager1.sql.model.HeightBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户身高表
 * created at 2021
 */
public class HeightDBDao {

    /** 数据表名称 */
    public static final String Table_NAME = "Height_table";

    /** 表的字段名 */
    public static String USER_NAME = "User_name";
    public static String USER_HEIGHT = "user_height";
    public static String HEIGHT_TIME = "height_time";
    public static String HEIGHT_ID = "_id";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */


    public HeightDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }

    /**
     * 添加至数据库

     */
    public void addHeight(HeightBean heightBean,String user_name){
        ContentValues values= new ContentValues();//存储方法
        values.put(USER_NAME,user_name);
        values.put(USER_HEIGHT, heightBean.user_height); // KEY 是列名，vlaue 是该列的值
        values.put(HEIGHT_TIME,heightBean.height_time);

        mDatabase.insert(Table_NAME, null, values);

    }


    public Cursor rawQuery(String sql, String[] args) {
        if (!DBConfig.HaveData(mDatabase,Table_NAME)){
            return null;
        }
        return mDatabase.rawQuery(sql, args);
    }



    public String[] getvalue(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from Height_table where User_name = ?", new String[]{name} );
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex  = mCursor.getColumnIndex(USER_HEIGHT);



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



    public void deleteHeightNum(String number){

        mDatabase.delete(Table_NAME, "user_height = ?", new String[] {number});

    }


    public List<HeightBean> getHeightNumByPage(String name){

        List<HeightBean> alldata = new ArrayList<HeightBean>();
        Cursor cursor = mDatabase.rawQuery("select * from Height_table where User_name = ? order by _id desc limit 20", new String[]{name} );
        while(cursor.moveToNext()){

            String user_height = cursor.getString(cursor.getColumnIndex(USER_HEIGHT));// 获得number 这列的值
            String height_time=cursor.getString(cursor.getColumnIndex(HEIGHT_TIME));
            HeightBean heightBean = new HeightBean(user_height,height_time);
            alldata.add(heightBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(500);// 休眠2秒比较耗时的情况
        return alldata;

    }




    public int getHeightCount(){

        Cursor cursor = mDatabase.query(Table_NAME, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);// 仅查了一列，count(*) 这一刻列

        cursor.close();

        return count;

    }


}
