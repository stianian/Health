package com.example.healthemanager1.util;

import com.example.healthemanager1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayUtils {

    public static String setDay() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String da = simpleDateFormat.format(date);
        return da;

    }


//    public static String format(int value) {
//        String tmpStr = String.valueOf(value);
//        if (value < 10) {
//            tmpStr = "0" + tmpStr;
//        }
//        return tmpStr;
//    }




    public static String[] breakfirst={"鸡蛋","牛奶","酸奶","豆浆","馒头"};
    public static String[] breakfirst1={"139","116","70","31","223"};
    public static String[] breakfirst0={"1","2","3","4","5"};
    public static int[] breakfirst2={R.mipmap.jidan,R.mipmap.niunai,R.mipmap.suannai,R.mipmap.doujiang,R.mipmap.mamtou};
    public static String[] lunch={"米饭","花菜","菠菜","洋葱","青椒"};
    public static String[] lunch1={"116","20","28","40","22"};
    public static String[] lunch0={"6","7","8","9","10"};
    public static int[] lunch2={R.mipmap.mifan,R.mipmap.huacai,R.mipmap.bocai,R.mipmap.yangcong,R.mipmap.qinjiao};
    public static String[] dinner={"煮面条","小豆粥","小米粥","鸡蛋","米饭"};
    public static String[] dinner1={"107","62","70","139","116"};
    public static String[] dinner0={"11","12","13","14","15"};
    public static int[] dinner2={R.mipmap.miantiao,R.mipmap.zhou,R.mipmap.mihzou,R.mipmap.jidan,R.mipmap.mifan};


    public static int[] drink={
            R.mipmap.kuangquanshui,R.mipmap.kafei,R.mipmap.naicha,R.mipmap.cha,R.mipmap.niunai,
            R.mipmap.suannai,R.mipmap.doujiang, R.mipmap.pinguozhi,R.mipmap.ningmengzhi,R.mipmap.chengzhi,
            R.mipmap.xuebi,R.mipmap.kele,R.mipmap.sudashui};

    public static String[] drinking={
            "白水","咖啡","奶茶","茶","牛奶",
            "酸奶","豆浆","苹果汁","柠檬汁","橙汁",
            "雪碧","可乐","苏打水"
    };


    public static String[] drinkWater={
            "100","80","80","100","80",
            "80","100","100","100","100",
            "100","100","100"
    };


    public static String[] drinkKCak={
            "0","50","60","1","66",
            "72","34","50","27","46",
            "44","43","0"
    };




    public static String tip1="同样是100ml的饮品，有可能每种饮品里面的含水量是不一样的。" +
            "所以，我们在计算摄入水分的时候，会对每种饮品的水分含量按比例进行计算，这样才能相对准确地计算大家的水分摄入量。";
    public static String tip2="即同样100ml的白水和咖啡，它们拥有不同的能量。";

}
