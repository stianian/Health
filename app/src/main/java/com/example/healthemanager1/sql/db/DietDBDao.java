package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.example.healthemanager1.sql.DBConfig;
import com.example.healthemanager1.sql.DietBean;
import com.example.healthemanager1.sql.model.HeightBean;

import java.util.ArrayList;
import java.util.List;

public class DietDBDao {


    /**
     * 数据表名称
     */
    public static final String Table_NAME = "Diet_table";

    /**
     * 表的字段名
     */
    public static String USER_NAME = "User_name";
    public static String DIET_ID = "_id";
    public static String DATA = "data";
    public static String DIET_NAME = "diet_name";
    public static String QUANTITY = "quantity";
    public static String NAME_ID = "name_id";


    private SQLiteDatabase mDatabase;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据库打开帮助类
     */


    public DietDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db) {
        mDatabase = db;
    }


    public void addDiet(DietBean dietBean, String username, String data) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(DATA, data);
        values.put(DIET_NAME, dietBean.dietName);
        values.put(QUANTITY, dietBean.quantity);
        values.put(NAME_ID, dietBean.nameID);
        mDatabase.insert(Table_NAME, null, values);
    }

    public List<DietBean> getDietNumByPage(String name, String data) {

        List<DietBean> alldata = new ArrayList<DietBean>();
        Cursor cursor = mDatabase.rawQuery("select * from Diet_table where User_name = ? and data = ? order by name_id desc ", new String[]{name, data});
        while (cursor.moveToNext()) {

            String diet_name = cursor.getString(cursor.getColumnIndex(DIET_NAME));// 获得number 这列的值
            String quantity = cursor.getString(cursor.getColumnIndex(QUANTITY));
            String nameID = cursor.getString(cursor.getColumnIndex(NAME_ID));

            DietBean dietBean = new DietBean(diet_name, quantity, nameID);
            alldata.add(dietBean);
        }

        //关闭cursor
        cursor.close();
        SystemClock.sleep(2000);// 休眠2秒比较耗时的情况
        return alldata;

    }
    public void deleteDietNum(String number){

        mDatabase.delete(Table_NAME, "diet_name = ?", new String[] {number});

    }

    public String[] getDietValue(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from Diet_table where User_name = ?  ", new String[]{name});
        String[] myFloats = new String[mCursor.getCount()];
        int ColumnIndex = mCursor.getColumnIndex(QUANTITY);


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



    public String[] getDietValue1(String name,String data){


        Cursor mCursor = mDatabase.rawQuery("select * from Diet_table where User_name = ? and data=? ", new String[]{name,data});
        String[] myFloats = new String[mCursor.getCount()];
        int ColumnIndex = mCursor.getColumnIndex(QUANTITY);


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

    public Cursor rawQuery(String sql, String[] args) {
        if (!DBConfig.HaveData(mDatabase,Table_NAME)){
            return null;
        }
        return mDatabase.rawQuery(sql, args);
    }



}
