package com.example.healthemanager1.sql.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthemanager1.sql.DBMaster;
import com.example.healthemanager1.sql.model.DrinkBean;
import com.example.healthemanager1.sql.model.HeightBean;

public class DrinkDBDao {


    /** 数据表名称 */
    public static final String Table_NAME = "DRINK_table";

    /** 表的字段名 */
    public static String DRINK_ID = "_id";
    public static String USER_NAME = "User_name";
    public static String DATA = "data";
    public static String DRINK_NAME = "drink_name";
    public static String DRINK_QUANTITY = "drink_quantity";
    public static String DRINK_NAME_ID = "drink_id";
    public static String DRINK_KCal= "drink_cal";




    private SQLiteDatabase mDatabase;
    /** 上下文 */
    private Context mContext;
    /** 数据库打开帮助类 */
    private DBMaster.DBOpenHelper mDbOpenHelper;

    public DrinkDBDao(Context context) {
        mContext = context;
    }

    public void setDatabase(SQLiteDatabase db){
        mDatabase = db;
    }


    public void addDrink(DrinkBean drinkBean, String user_name){
        ContentValues values= new ContentValues();//存储方法
        values.put(USER_NAME,user_name);
        values.put(DRINK_NAME, drinkBean.drinkName); // KEY 是列名，vlaue 是该列的值
        values.put(DRINK_QUANTITY,drinkBean.drinkQuantity);
        values.put(DATA,drinkBean.drinkData);
        values.put(DRINK_KCal,drinkBean.drinkCal);
        values.put(DRINK_NAME_ID,drinkBean.drinkID);



        mDatabase.insert(Table_NAME, null, values);

    }

    public float getvalue(String name,String data,int i){


        Cursor mCursor = mDatabase.rawQuery("select * from DRINK_table where User_name = ? and data = ?", new String[]{name,data} );
        String[] myFloats = new String[mCursor.getCount()];

        float mFloat=0;
        int ColumnIndex  =0;
        switch (i){
            case 0:
                ColumnIndex = mCursor.getColumnIndex(DRINK_QUANTITY);
                break;
            case 1:
                ColumnIndex= mCursor.getColumnIndex(DRINK_KCal);
                break;

            default:
                break;
        }


        while(mCursor.moveToNext()){// cursor.moveToNext() 向下移动一行,如果有内容，返回true

            for (int j = 0; j < mCursor.getCount(); j++)
            {
                myFloats[j] = mCursor.getString(ColumnIndex);
                mCursor.moveToNext();
                mFloat+=Float.parseFloat(myFloats[j]);
            }

        }
        mCursor.close();
        return mFloat;
    }


    public String[] getDrinkValue(String name,String data,int i){


        Cursor mCursor = mDatabase.rawQuery("select * from DRINK_table where User_name = ? and data = ?", new String[]{name,data} );
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex  =0;
        switch (i){
            case 0:
                ColumnIndex = mCursor.getColumnIndex(DRINK_NAME);
                break;
            case 1:
                ColumnIndex= mCursor.getColumnIndex(DRINK_QUANTITY);
                break;
            case 2:
                ColumnIndex= mCursor.getColumnIndex(DRINK_NAME_ID);
                break;
            case 3:
                ColumnIndex= mCursor.getColumnIndex(DATA);
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

    public void deleteDrinkNum(String number){

        mDatabase.delete(Table_NAME, "drink_name = ?", new String[] {number});

    }
    public String[] getDrinkData(String name){


        Cursor mCursor = mDatabase.rawQuery("select * from DRINK_table where User_name = ? ", new String[]{name,} );
        String[] myFloats = new String[mCursor.getCount()];

        int ColumnIndex= mCursor.getColumnIndex(DATA);


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


}
