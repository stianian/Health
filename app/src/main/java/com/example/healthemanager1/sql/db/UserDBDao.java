
package com.example.healthemanager1.sql.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthemanager1.sql.DBConfig;
import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.UserBean;

/**
 * 用户数据表操作类
 * @author
 * created at 2021/01
 */
public class UserDBDao {
    /** 数据表名称 */
//    public static final String Table_Name = "user_table";

    public static final String Table_Name = "user_table";
    /** 表的字段名 */

    public static String USER_NAME = "User_name";
    public static String USER_ID = "User_id";
    public static String USER_SEX = "User_sex";
    public static String USER_AGE = "User_age";
    public static String USER_DATA ="User_data";

    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private  DBMaster.DBOpenHelper mDbOpenHelper;

    public UserDBDao(Context context) {

        mContext = context;
    }



    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }





    /**
     * 添加至数据库

     */
    public void addUser(UserBean userBean){
        ContentValues values= new ContentValues();//存储方法
        values.put(USER_NAME, userBean.username); // KEY 是列名，vlaue 是该列的值
        values.put(USER_ID,userBean.user_id);
        values.put(USER_SEX, userBean.user_sex);
        values.put(USER_AGE,userBean.user_age);
        values.put(USER_DATA,userBean.user_data);
        mDatabase.insert(Table_Name, null, values);

    }


    public Cursor rawQuery(String sql, String[] args) {
        if (!DBConfig.HaveData(mDatabase,Table_Name)){
            return null;
        }
        return mDatabase.rawQuery(sql, args);
    }







    public String getUser(int i){


        Cursor mCursor = mDatabase.rawQuery("select * from user_table order by User_id desc limit 1", null);
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex=0;
        switch (i){
            case 0:
                ColumnIndex= mCursor.getColumnIndex(USER_NAME);
                break;
            case 1:
                ColumnIndex=mCursor.getColumnIndex(USER_SEX);
                break;
            case 2:
                ColumnIndex=mCursor.getColumnIndex(USER_AGE);
                break;
            case 3:
                ColumnIndex=mCursor.getColumnIndex(USER_DATA);
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
        return myFloats[0];
    }



    public String[] getvalue(){


        Cursor mCursor = mDatabase.rawQuery("select * from user_table ", null);
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex = mCursor.getColumnIndex(USER_NAME);



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







    public int getUserCount(){

        Cursor cursor = mDatabase.query(Table_Name, new String[] {"count(*)"}, null, null, null, null, null);

        cursor.moveToNext();
        int count = cursor.getInt(0);// 仅查了一列，count(*) 这一刻列

        cursor.close();

        return count;

    }





}
