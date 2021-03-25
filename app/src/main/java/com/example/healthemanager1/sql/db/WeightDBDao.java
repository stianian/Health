package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;


import com.example.healthemanager1.sql.DBConfig;
import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.WeightBean;

import java.util.ArrayList;
import java.util.List;

public class WeightDBDao {
    /** 数据表名称 */
    public static final String Table_NAME = "Weight_table";

    /** 表的字段名 */
    public static String USER_NAME = "User_name";
    public static String USER_WEIGHT = "user_weight";
    public static String WEIGHT_TIME = "weight_time";
    public static String WEIGHT_ID = "_id";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public WeightDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }





    public void addWeight(WeightBean weightBean,String user_name){
        ContentValues values= new ContentValues();
        values.put(USER_NAME,user_name);
        values.put(USER_WEIGHT, weightBean.user_weight);
        values.put(WEIGHT_TIME,weightBean.weight_time);

        mDatabase.insert(Table_NAME, null, values);

    }


    public Cursor rawQuery(String sql, String[] args) {
        if (!DBConfig.HaveData(mDatabase,Table_NAME)){
            return null;
        }
        return mDatabase.rawQuery(sql, args);
    }



    public String[] getvalue(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from Weight_table where User_name = ? ",  new String[]{name});
        String[] myFloats = new String[mCursor.getCount()];
        int ColumnIndex  = mCursor.getColumnIndex(USER_WEIGHT);



        while(mCursor.moveToNext()){

            for (int j = 0; j < mCursor.getCount(); j++)
            {
                myFloats[j] = mCursor.getString(ColumnIndex);

                mCursor.moveToNext();
            }

        }
        mCursor.close();
        return myFloats;
    }







    public List<WeightBean> getWeightNumByPage(String name){

        List<WeightBean> alldata = new ArrayList<WeightBean>();
        Cursor cursor = mDatabase.rawQuery("select * from Weight_table  where User_name = ? order by _id desc limit 20;", new String[]{name});
        while(cursor.moveToNext()){

            String user_weight = cursor.getString(cursor.getColumnIndex(USER_WEIGHT));
            String weight_time=cursor.getString(cursor.getColumnIndex(WEIGHT_TIME));
            WeightBean weightBean = new WeightBean(user_weight,weight_time);
            alldata.add(weightBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(500);
        return alldata;

    };




    public int getWeightCount(){

        Cursor cursor = mDatabase.query(Table_NAME, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);

        cursor.close();

        return count;

    }


    public void deleteWeightNum(String number){

        mDatabase.delete(Table_NAME, "user_weight = ?", new String[] {number});

    }




}
