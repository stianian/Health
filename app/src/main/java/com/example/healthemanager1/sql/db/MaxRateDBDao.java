package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.MaxRateBean;

import java.util.ArrayList;
import java.util.List;

public class MaxRateDBDao {
    /** 数据表名称 */
    public static final String TABLE_NAME = "Max_rate";

    /** 表的字段名 */
    public static String USER_NAME = "User_name";
    public static String USER_MAXRATA = "User_MaxRate";
    public static String MAXRATA_TIME = "MaxRate_time";
    public static String MAXRATA_ID = "_id";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public MaxRateDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }


    public void addMax(MaxRateBean maxRateBean,String user_name){
        ContentValues values= new ContentValues();//存储方法
        values.put(USER_NAME,user_name);
        values.put(USER_MAXRATA,maxRateBean.User_MaxRate ); // KEY 是列名，vlaue 是该列的值

        values.put(MAXRATA_TIME,maxRateBean.MaxRate_time);
        mDatabase.insert(TABLE_NAME, null, values);

    }


    /**
     * 查询
     * @param name
     * @return
     */
    public String[] getvalue(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from Max_rate where User_name = ?", new String[]{name});
        String[] myFloats = new String[mCursor.getCount()];
        int ColumnIndex  = mCursor.getColumnIndex(USER_MAXRATA);

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


    /**
     * 查询
     * @param name
     * @return
     */
    public List<MaxRateBean> getMaxNumByPage(String name){

        List<MaxRateBean> alldata = new ArrayList<MaxRateBean>();
        Cursor cursor = mDatabase.rawQuery("select * from Max_rate where User_name = ? order by _id desc limit 20;", new String[]{name});
        while(cursor.moveToNext()){

            String user_max = cursor.getString(cursor.getColumnIndex(USER_MAXRATA));//这列的值
            String maxrata_time = cursor.getString(cursor.getColumnIndex(MAXRATA_TIME));

            MaxRateBean maxRateBean = new MaxRateBean(user_max,maxrata_time);
            alldata.add(maxRateBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(500);// 休眠2秒，模拟黑名单比较多，比较耗时的情况
        return alldata;

    };




    public int getMaxCount(){

        Cursor cursor = mDatabase.query(TABLE_NAME, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);// 仅查了一列，count(*) 这一刻列

        cursor.close();

        return count;

    }

    public void deleteMaxNum(String number){

        mDatabase.delete(TABLE_NAME, "User_MaxRate = ?", new String[] {number});

    }

}
