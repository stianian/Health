package com.example.healthemanager1.ui.dashboard.SleepManager;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.ui.notifications.NotificationsViewModel;
import com.example.healthemanager1.ui.dashboard.SleepManager.widget.CircleProgressView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * create 21 2.2
 */
public class pageFragment2 extends Fragment {
    private NotificationsViewModel notificationsViewModel;


    private String user_name1= MyApplication.name.getName() ;


    private ColumnChartView ColumnChartView;

    private String[] arr;
    private CircleProgressView circleProgressView;
    private TextView sp1,sp2;
    private Button btSeven,btThirty;
    private int selectDay=7;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fg_da_sleep_result, container, false);
        ColumnChartView=(ColumnChartView) view.findViewById(R.id.column_chart);
        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleProgressView);
        sp1=(TextView)view.findViewById(R.id.sp1);
        sp2=(TextView)view.findViewById(R.id.sp2);
        btSeven=(Button)view.findViewById(R.id.seven_day);
        btThirty=(Button)view.findViewById(R.id.thirty_day);

        btSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDay=7;
                initDate();
            }
        });
        btThirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDay=30;
                initDate();
            }
        });
        new Thread().start();


        initDate();

        return view;


    }

    public class Thread extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        arr= MyApplication.mDBMaster.planDBDao.getvalue2(user_name1,2);
                        handler.sendEmptyMessage(1);
                    } catch (Exception e) {
                    }

                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }

            }

        }

    }






    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://子线程获得了数据，开始刷新页面
                    if (arr.length>0){
                        float xp = 0,progress=0;
                        float xp1 =0,progress1=0;
                        for(int i=0;i<arr.length;i++){
                            xp+=xProgress(arr[i]);
                            progress+=Progress(arr[i]);
                        }
                        xp1=xp/arr.length/24*100;
                        progress1=progress/arr.length/24*100;


                        String str1=xxx(xp/arr.length);
                        String str2=xxx(progress/arr.length);

                        circleProgressView.setProgress(xp1,progress1,arr.length);
                        sp1.setText(str1);
                        sp2.setText(str2);
                    }else {
                        circleProgressView.setProgress(0,90,6);
                        sp1.setText("无数据");
                        sp2.setText("无数据");
                    }


                    break;
            }
        }

    };


    /**
     * 时间转小数
     * @param ff
     * @return
     */
    private String xxx(float ff){
        String[] strs = null;
        String x = null;
        String y=null;
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        String str=df.format((float)ff);
            //判断是否包含小数
            if(str.lastIndexOf(".")!=-1)
            {

                strs=str.split("\\.");
//                System.out.println("整数部分:"+strs[0]);
//                System.out.println("小数部分:"+strs[1]);
                x= format(Integer.parseInt(strs[1])*60/100);
                y=strs[0]+":"+x;
            }

            return y;
    }


    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    /**
     * 开始百分比
     * @param str
     * @return
     */
    private float xProgress(String str){
        float hour1,minute1;
        hour1=Float.parseFloat(str.substring(0,2));
        minute1=Float.parseFloat(str.substring(3,5));
//        Log.d("xty1", String.valueOf(minute1));
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        String m=df.format((float)minute1/60);
//        Log.d("xty2", String.valueOf(m));
        float rMinute= Float.parseFloat(m);

        String string=df.format((float)(hour1+rMinute));
        float rTime=Float.parseFloat(string);
        return rTime;
    }

    /**
     * 结束百分比
     * @param str
     * @return
     */
    private float Progress(String str){
        float hour1,minute1;
        hour1=Float.parseFloat(str.substring(6,8));
        minute1=Float.parseFloat(str.substring(9,11));
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        String m=df.format((float)minute1/60);
        float rMinute= Float.parseFloat(m);
        String string=df.format((float)(hour1+rMinute));
        float rTime=Float.parseFloat(string);
        return rTime;
    }

    /**
     * 时间段长度
     * @param range
     * @return
     */
    private float rangeTime(String range){
        float rHour;
        String two;
        String first;
        if (!Character.isDigit(range.charAt(1))){
            two=range.substring(3,5);
            char first1 = range.charAt(0);
            rHour=Float.parseFloat(String.valueOf(first1));
        }else {
            first=range.substring(0,2);
            rHour=Float.parseFloat(first);
            two=range.substring(4,6);
        }
        int minute=Integer.parseInt(two);
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        String str=df.format((float)minute/60);
        float rMinute= Float.parseFloat(str);
        float rangeTime=(float)(Math.round((rHour+rMinute)*100))/100;
        return rangeTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Date getNextDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        date = calendar.getTime();
        return date;
    }


    /**
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();

        List<Column> columnList = new ArrayList<>(); //柱子列表
        List<SubcolumnValue> subcolumnValueList;     //子柱列表（即一个柱子，因为一个柱子可分为多个子柱）
        List<AxisValue> axisValues = new ArrayList<>();//创建x轴数据

//        int length=range.length;
//        String[] range1=new String[length];
//        String[] range2=new String[7];
//        for(int i=0;i<range.length;i++){
//            range1[i]=String.valueOf(rangeTime(range[i]));
//        }
//        if(length<7){
//            for(int i=0;i<7-length;i++){
//                range2[i]="0";
//            }
//            for(int i=7-length;i<7;i++){
//                range2[i]=range1[i-7+length];
//            }
//        } else {
//            for(int i=0;i<7;i++){
//                range2[i]=range1[length-7+i];
//            }
//
//        }

        for (int i = 0; i < selectDay; ++i) {
            subcolumnValueList = new ArrayList<>();//每个子柱的集合
            String d = simpleDateFormat.format(getNextDate(date1,i-selectDay+1));
            String x=null;
            x=MyApplication.mDBMaster.planDBDao.getSleepValue(user_name1,d);
            float range1;
            if(x==null){
                range1=0;
            }else{
                range1=rangeTime(x);
            }

            subcolumnValueList.add(new SubcolumnValue(range1, R.color.s2));//每个子柱集合的数据

            axisValues.add(new AxisValue(i).setLabel(d));


            Column column = new Column(subcolumnValueList);//创建子柱数据
            column.setHasLabels(false);                    //设置列标签
            columnList.add(column);//添加柱子数据
//            rangeTime(range[i]);
        }
        ColumnChartData mColumnChartData = new ColumnChartData(columnList);        //设置数据
        /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis();//设置横坐标柱子下面的分类
        axisX.setValues(axisValues);
        axisX.setName("时间");    //设置横轴名称
        axisX.setMaxLabelChars(7);


        Axis axisY = new Axis();
        axisY.setHasLines(true);
        axisY.setName("");    //设置竖轴名称

        float[] dataY = {0,4,8,12,16,20};//Y轴的标注
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < dataY.length; i++){
            AxisValue value = new AxisValue(dataY[i]);
            values.add(value);
        }

        axisY.setValues(values);


        mColumnChartData.setAxisXBottom(axisX); //设置横轴
        mColumnChartData.setAxisYLeft(axisY);   //设置竖轴
        ColumnChartView.setZoomEnabled(false);//不可点击
        ColumnChartView.setMaxZoom(1);
        //以上所有设置的数据、坐标配置都已存放到mColumnChartData中，接下来给mColumnChartView设置这些配置
        ColumnChartView.setColumnChartData(mColumnChartData);



        Viewport v = ColumnChartView.getMaximumViewport();//设置ｙ轴的长度
        v.top = 21;
        v.bottom=0;
        ColumnChartView.setCurrentViewport(v);

        ColumnChartView.setCurrentViewport(v);
    }













}
