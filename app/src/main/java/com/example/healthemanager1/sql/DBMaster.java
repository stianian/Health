package com.example.healthemanager1.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthemanager1.sql.db.DietDBDao;
import com.example.healthemanager1.sql.db.DrinkDBDao;
import com.example.healthemanager1.sql.db.HeightDBDao;
import com.example.healthemanager1.sql.db.MaxRateDBDao;
import com.example.healthemanager1.sql.db.PlanDBDao;
import com.example.healthemanager1.sql.db.StaticDBDao;
import com.example.healthemanager1.sql.db.UserDBDao;
import com.example.healthemanager1.sql.db.WeightDBDao;

import static com.example.healthemanager1.sql.DBConfig.DB_NAME;
import static com.example.healthemanager1.sql.DBConfig.DB_VERSION;

/**
 * 数据库总操作类
 */
public class DBMaster {

    /** 上下文 */
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBOpenHelper mDbOpenHelper;

    /** 数据表操作类实例化 */
   public UserDBDao userDBDao;
   public HeightDBDao heightDBDao;
   public WeightDBDao weightDBDao;
   public DrinkDBDao drinkDBDao;
   public MaxRateDBDao maxRateDBDao;
   public StaticDBDao staticDBDao;

   public PlanDBDao planDBDao;
   public DietDBDao dietDBDao;


    public DBMaster(Context context){
        mContext = context;
        userDBDao = new UserDBDao(mContext);
        heightDBDao = new HeightDBDao(mContext);
        weightDBDao =new WeightDBDao(mContext);
        drinkDBDao =new DrinkDBDao(mContext);
        maxRateDBDao =new MaxRateDBDao(mContext);
        staticDBDao =new StaticDBDao(mContext);
        planDBDao = new PlanDBDao(mContext);
        dietDBDao = new DietDBDao(mContext);
    }

    /**
     * 打开数据库
     */
    public void openDataBase() {
        mDbOpenHelper = new DBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase();//获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase();//获取只读数据库
        }
        // 设置数据库的SQLiteDatabase
        userDBDao.setDatabase(mDatabase);
        heightDBDao.setDatabase(mDatabase);
        weightDBDao.setDatabase(mDatabase);
        drinkDBDao.setDatabase(mDatabase);
        maxRateDBDao.setDatabase(mDatabase);
        staticDBDao.setDatabase(mDatabase);
        planDBDao.setDatabase(mDatabase);
        dietDBDao.setDatabase(mDatabase);
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    /** 创建该数据库下user_table表的语句 */
    private static final String mUserSqlStr = "create table if not exists " + UserDBDao.Table_Name + " (" +
            UserDBDao.USER_NAME + " varchar(20) unique, " +
            UserDBDao.USER_ID + " INTEGER primary key autoincrement, " +
            UserDBDao.USER_SEX + " varchar(20), " +
            UserDBDao.USER_AGE + " varchar(20)," +
            UserDBDao.USER_DATA + " varchar(20) " +

            ");";


    /** 创建该数据库下height_table表的语句 */
    private static final String mHeightSqlStr = "create table if not exists " + HeightDBDao.Table_NAME + " (" +
            HeightDBDao.HEIGHT_ID + " INTEGER primary key autoincrement, " +
            HeightDBDao.USER_NAME + " varchar(20) ," +
            HeightDBDao.USER_HEIGHT + " varchar(20) , " +

            HeightDBDao.HEIGHT_TIME + " varchar(20) );";


    private static final String mWeightSqlStr = "create table if not exists " + WeightDBDao.Table_NAME + " (" +
            WeightDBDao.USER_NAME + " varchar ," +
            WeightDBDao.USER_WEIGHT + " varchar ," +
            WeightDBDao.WEIGHT_ID + " INTEGER primary key autoincrement, " +
            WeightDBDao.WEIGHT_TIME + " varchar );";




    private static final String mBMISqlStr = "create table if not exists " + DrinkDBDao.Table_NAME + " (" +
            DrinkDBDao.DRINK_ID + " INTEGER primary key autoincrement, " +
            DrinkDBDao.USER_NAME + " varchar ,"+
            DrinkDBDao.DATA + " varchar ," +
            DrinkDBDao.DRINK_NAME_ID + " varchar ," +
            DrinkDBDao.DRINK_KCal + " varchar ," +
            DrinkDBDao.DRINK_NAME + " varchar ," +
            DrinkDBDao.DRINK_QUANTITY + " varchar );";



    private static final String mMaxRateSqlStr = "create table if not exists " + MaxRateDBDao.TABLE_NAME + " (" +
            MaxRateDBDao.USER_NAME + " varchar ," +
            MaxRateDBDao.USER_MAXRATA + " varchar  , " +
            MaxRateDBDao.MAXRATA_ID + " INTEGER primary key autoincrement, " +
            MaxRateDBDao.MAXRATA_TIME + " varchar );";



    private static final String mStaticRateSqlStr = "create table if not exists " + StaticDBDao.TABLE_NAME + " (" +
            StaticDBDao.USER_NAME + " varchar ," +
            StaticDBDao.USER_STATICRATA + " varchar , " +
            StaticDBDao.STATICRATE_ID + " INTEGER primary key autoincrement, " +
            StaticDBDao.STATICRATE_TIME + " varchar );";


    private static final String mPlanSqlStr = "create table if not exists " + PlanDBDao.Table_NAME + " (" +
            PlanDBDao.USER_NAME + " varchar , " +
            PlanDBDao.ID + " INTEGER primary key autoincrement, " +
            PlanDBDao.EXERCISE + " varchar ," +

            PlanDBDao.RANGE_TIME + " varchar ," +
            PlanDBDao.FOOD + " varchar ," +
            PlanDBDao.EXERCISE_TIME + " varchar ," +

            PlanDBDao.SLEEP + " varchar unique," +

            PlanDBDao.SLEEP_TIME + " varchar );";



    private static final String mDietSqlStr = "create table if not exists " + DietDBDao.Table_NAME + " (" +

            DietDBDao.DIET_ID + " INTEGER primary key autoincrement, " +
            DietDBDao.USER_NAME + " varchar ," +
            DietDBDao.DATA + " varchar ," +
            DietDBDao.DIET_NAME + " varchar ," +
            DietDBDao.QUANTITY + " varchar ," +
            DietDBDao.NAME_ID + " varchar );";


    /**
     * 删除表语句
     */

    private static final String mUserDelStr = "DROP TABLE IF EXISTS " + UserDBDao.Table_Name;


    private static final String mHeightDelStr = "DROP TABLE IF EXISTS " + HeightDBDao.Table_NAME;


    private static final String mWeightDelStr = "DROP TABLE IF EXISTS " + WeightDBDao.Table_NAME;
    private static final String mBMIDelStr = "DROP TABLE IF EXISTS " + DrinkDBDao.Table_NAME;
    private static final String mMaxRateDelStr = "DROP TABLE IF EXISTS " + MaxRateDBDao.TABLE_NAME;
    private static final String mStaticRateDelStr = "DROP TABLE IF EXISTS " + StaticDBDao.TABLE_NAME;


    /**
     * 数据表打开帮助类
     */
    public static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mUserSqlStr);
            db.execSQL(mHeightSqlStr);
            db.execSQL(mWeightSqlStr);
            db.execSQL(mBMISqlStr);
            db.execSQL(mMaxRateSqlStr);
            db.execSQL(mStaticRateSqlStr);
            db.execSQL(mPlanSqlStr);
            db.execSQL(mDietSqlStr);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(mUserDelStr);
            db.execSQL(mHeightDelStr);
            db.execSQL(mWeightDelStr);
            db.execSQL(mBMIDelStr);
            db.execSQL(mMaxRateDelStr);
            db.execSQL(mStaticRateDelStr);

            onCreate(db);
        }
    }
}
