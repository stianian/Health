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
    private HPillarView hPillarView;
    private List chartList = new ArrayList();
    private List chartList1 = new ArrayList();
    private List chartList2 = new ArrayList();

    private String[] quantity;
    private String[] drinkName;
    int total=0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_da_drink_result, container, false);
        ColumnChartView=(ColumnChartView) view.findViewById(R.id.column_chart_drink);
        hPillarView= (HPillarView)view.findViewById(R.id.pillar_h);
        new Thread1().start();
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
                case 1://?????????????????????????????????????????????

                    if (drinkName.length == 0||quantity.length==0) {
//                        progressBar.setVisibility(View.GONE);
//                        tvDesc.setText("????????????");
                    } else {
                        if(chartList==null||chartList1==null){

                            for(int i=0;i<drinkName.length;i++){
                                float value= Float.parseFloat(quantity[i])/total;
                                float dd = (float)(Math.round(value*10000))/100;//????????????
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
                                float dd = (float)(Math.round(value*10000))/100;//????????????
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
        List<Column> columnList = new ArrayList<>(); //????????????
        List<SubcolumnValue> subcolumnValueList;     //???????????????????????????????????????????????????????????????????????????
        List<AxisValue> axisValues = new ArrayList<>();//??????x?????????

        int average=0;
        for (int i = 0; i < 7; ++i) {
            subcolumnValueList = new ArrayList<>();//?????????????????????
            String d = simpleDateFormat.format(getNextDate(date1,i-6));
            float x=0;
            x=MyApplication.mDBMaster.drinkDBDao.getvalue(user_name1, d,0);
            average+=x;
            subcolumnValueList.add(new SubcolumnValue(x, R.color.s2));//???????????????????????????
            axisValues.add(new AxisValue(i).setLabel(d));
            Column column = new Column(subcolumnValueList);//??????????????????
            column.setHasLabels(false);                    //???????????????
            columnList.add(column);//??????????????????

        }
        ColumnChartData mColumnChartData = new ColumnChartData(columnList);        //????????????
        /*===== ????????????????????? =====*/
        Axis axisX = new Axis();//????????????????????????????????????
        axisX.setValues(axisValues);
        axisX.setName(" ?????? ????????? "+"("+ average/7 +" ml)");    //??????????????????
        axisX.setMaxLabelChars(7);
        Axis axisY = new Axis();
        axisY.setHasLines(true);
        axisY.setName("??????");    //??????????????????

        float[] dataY = {0,200,400,600,800,1000,1200,1400,1600,1800,2000};//Y????????????
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < dataY.length; i++){
            AxisValue value = new AxisValue(dataY[i]);
            values.add(value);
        }

        axisY.setValues(values);


        mColumnChartData.setAxisXBottom(axisX); //????????????
        mColumnChartData.setAxisYLeft(axisY);   //????????????
        ColumnChartView.setZoomEnabled(false);//????????????
        ColumnChartView.setMaxZoom(1);
        ColumnChartView.setColumnChartData(mColumnChartData);

        Viewport v = ColumnChartView.getMaximumViewport();//?????????????????????
        v.top = 2100;
        v.bottom=0;
        ColumnChartView.setCurrentViewport(v);
        ColumnChartView.setCurrentViewport(v);
    }

}
