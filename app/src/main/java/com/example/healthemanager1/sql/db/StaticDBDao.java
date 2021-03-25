package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.StaticBean;

import java.util.ArrayList;
import java.util.List;

public class StaticDBDao {

    /** 数据表名称 */
    public static final String TABLE_NAME = "Static_rate";

    /** 表的字段名 */
    public static String USER_NAME = "User_name";
    public static String USER_STATICRATA = "User_StaticRate";
    public static String STATICRATE_TIME = "StaticRate_time";
    public static String STATICRATE_ID = "_id";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public StaticDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }





    public void addsta(StaticBean staticBean,String user_name){
        ContentValues values= new ContentValues();//存储方法
        values.put(USER_NAME,user_name);
        values.put(USER_STATICRATA,staticBean.User_StaticRate); // KEY 是列名，vlaue 是该列的值

        values.put(STATICRATE_TIME,staticBean.StaticRate_time);
        mDatabase.insert(TABLE_NAME, null, values);

    }


    public String[] getvalue(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from Static_rate where User_name = ?", new String[]{name} );
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex  = mCursor.getColumnIndex(USER_STATICRATA);



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






    public List<StaticBean> getStaticNumByPage(String name){

        List<StaticBean> alldata = new ArrayList<StaticBean>();
        Cursor cursor = mDatabase.rawQuery("select * from Static_rate where User_name = ? order by _id desc limit 20;", new String[]{name});
        while(cursor.moveToNext()){

            String user_sta = cursor.getString(cursor.getColumnIndex(USER_STATICRATA));
            String sta_time = cursor.getString(cursor.getColumnIndex(STATICRATE_TIME));

            StaticBean staticBean = new StaticBean(user_sta,sta_time);
            alldata.add(staticBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(500);// 休眠2秒，模拟黑名单比较多，比较耗时的情况
        return alldata;

    };




    public int getStaCount(){

        Cursor cursor = mDatabase.query(TABLE_NAME, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);// 仅查了一列，count(*) 这一刻列

        cursor.close();

        return count;

    }



    public void deleteStaticNum(String number){

        mDatabase.delete(TABLE_NAME, "User_StaticRate = ?", new String[] {number});

    }










}
