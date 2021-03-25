package com.example.healthemanager1.ui.dashboard.Drink;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.ui.dashboard.Drink.widget.HPillarView;
import com.example.healthemanager1.util.DayUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

public class FragmentThirsty2 extends Fragment {


    private String user_name1= MyApplication.name.getName() ;
    private ColumnChartView ColumnChartView;
    HPillarView hPillarView;
    private List chartList = new ArrayList();
    private List chartList1 = new ArrayList();
    private List chartList2 = new ArrayList();

    String[] quantity;
    String[] drinkName;
    int total=0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_da_drink_result, container, false);
        ColumnChartView=(ColumnChartView) view.findViewById(R.id.column_chart_drink);
        hPillarView= (HPillarView)view.findViewById(R.id.pillar_h);



        new Thread1().start();
        //设置数据（一步搞定）
//        hPillarView.setData(chartList,chartList1);



        initDate();
        return view;


    }







    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {

                        quantity=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),1);

                        drinkName=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),0);
                        total=0;
                        for (int i=0;i<quantity.length;i++){
                            total+=Integer.parseInt(quantity[i]);
                        }

//                        Log.d("xtt",total+"");


                        handler.sendEmptyMessage(1);
                    } catch (Exception e) {
                    }

                    Thread.sleep(10000);
                } catch (InterruptedException e) {

                }

            }

        }

    }




    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://子线程获得了数据，开始刷新页面



                    if (drinkName.length == 0||quantity.length==0) {
//                        progressBar.setVisibility(View.GONE);
//                        tvDesc.setText("没有数据");
                    } else {
                        if(chartList==null||chartList1==null){

                            for(int i=0;i<drinkName.length;i++){
                                float value= Float.parseFloat(quantity[i])/total;
                                float dd = (float)(Math.round(value*10000))/100;//保留两位
                                chartList.add(dd);
                                chartList1.add(drinkName[i]);
                                chartList2.add(quantity[i]+"ml");

                            }
                            hPillarView.setData(chartList,chartList1,chartList2);

                        }else{
                            chartList.clear();
                            chartList1.clear();
                            for(int i=0;i<drinkName.length;i++){

                                float value= Float.parseFloat(quantity[i])/total;
                                float dd = (float)(Math.round(value*10000))/100;//保留两位
                                chartList.add(dd);
                                chartList1.add(drinkName[i]);
                                chartList2.add(quantity[i]+"ml");

                            }
                            hPillarView.setData(chartList,chartList1,chartList2);
                        }





                        }


                    break;
            }
        }


    };






    private static Date getNextDate(Date date,int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        date = calendar.getTime();

        return date;
    }



    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();

        List<Column> columnList = new ArrayList<>(); //柱子列表
        List<SubcolumnValue> subcolumnValueList;     //子柱列表（即一个柱子，因为一个柱子可分为多个子柱）
        List<AxisValue> axisValues = new ArrayList<>();//创建x轴数据

        int average=0;

//        float[] x1={0,0,100,200,400,0,300};
        for (int i = 0; i < 7; ++i) {
            subcolumnValueList = new ArrayList<>();//每个子柱的集合
            String d = simpleDateFormat.format(getNextDate(date1,i-6));
            float x=0;
            x=MyApplication.mDBMaster.drinkDBDao.getvalue(user_name1, d,0);


            average+=x;
            subcolumnValueList.add(new SubcolumnValue(x, R.color.s2));//每个子柱集合的数据

            axisValues.add(new AxisValue(i).setLabel(d));


            Column column = new Column(subcolumnValueList);//创建子柱数据
            column.setHasLabels(false);                    //设置列标签
            columnList.add(column);//添加柱子数据

        }
        ColumnChartData mColumnChartData = new ColumnChartData(columnList);        //设置数据
        /*===== 坐标轴相关设置 =====*/
        Axis axisX = new Axis();//设置横坐标柱子下面的分类
        axisX.setValues(axisValues);
        axisX.setName(" —— 平均值 "+"("+ average/7 +" ml)");    //设置横轴名称
        axisX.setMaxLabelChars(7);


        Axis axisY = new Axis();
        axisY.setHasLines(true);
        axisY.setName("数据");    //设置竖轴名称

        float[] dataY = {0,200,400,600,800,1000,1200,1400,1600,1800,2000};//Y轴的标注
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
        v.top = 2100;
        v.bottom=0;
        ColumnChartView.setCurrentViewport(v);

        ColumnChartView.setCurrentViewport(v);
    }




}
