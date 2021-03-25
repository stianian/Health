package com.example.healthemanager1.ui.dashboard.SleepManager.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.model.PlanBean;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * 自定义dialog
 * 选择时间区间
 */
public class CustomFragmentDialog extends BottomSheetDialogFragment  implements NumberPicker.Formatter{
    NumberPicker hourPicker,minutePicker,hourPicker1,minutePicker1;

    String mHour,mMinute,mHour1,mMinute1;
    String year,month,day;
    int hour;
    int minute;
    int hour1;
    int minute1;

    Button button;

    public CustomFragmentDialog( String year,String month,String day) {

        this.year=year;
        this.month=month;
        this.day=day;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_sleep,container, false);

        hourPicker=(NumberPicker)view.findViewById(R.id.hourpicker);
        minutePicker=(NumberPicker)view.findViewById(R.id.minuteicker);
        hourPicker1=(NumberPicker)view.findViewById(R.id.hourpicker1);
        minutePicker1=(NumberPicker)view.findViewById(R.id.minuteicker1);


        button=(Button) view.findViewById(R.id.add_d);

        initViews();

        return view;
    }



    private void initViews() {

        hourPicker.setFormatter(this);



        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);
        hourPicker.setValue(21);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                hour=newVal;
                mHour=format(newVal);

            }
        });

        minutePicker.setFormatter(this);


        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        minutePicker.setValue(0);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                minute=newVal;
                mMinute=format(newVal);
            }
        });

        //设置为对当前值不可编辑
        hourPicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        minutePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        //这里设置为不循环显示，默认值为true
        hourPicker.setWrapSelectorWheel(true);
        minutePicker.setWrapSelectorWheel(true);




        hourPicker1.setFormatter(this);


        hourPicker1.setMaxValue(23);
        hourPicker1.setMinValue(0);
        hourPicker1.setValue(7);
        hourPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                hour1=newVal;
                mHour1=format(newVal);

            }
        });

        minutePicker1.setFormatter(this);


        minutePicker1.setMaxValue(56);
        minutePicker1.setMinValue(0);
        minutePicker1.setValue(0);
        minutePicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                minute1=newVal;
                mMinute1=format(newVal);

            }
        });

        //设置为对当前值不可编辑
        hourPicker1.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        minutePicker1.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        //这里设置为不循环显示，默认值为true
        hourPicker1.setWrapSelectorWheel(true);
        minutePicker1.setWrapSelectorWheel(true);


        /**
         * 将今天的休息存入数据库
         */


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mHour==null||mMinute==null||mHour1==null||mMinute1==null){
                    Toast.makeText(getContext(), "记录失败，请重新记录", Toast.LENGTH_SHORT).show();
                }else {
                    int mon=Integer.parseInt(month)+1;

                    String s = year+"-"+format(mon)+"-"+format(Integer.parseInt(day));//日期
                    String string=mHour+":"+mMinute+"-"+mHour1+":"+mMinute1;//时间段
                    String tt=getTime(hour,minute,hour1,minute1);//睡眠时长


                    String user_name1= MyApplication.name.getName() ;//姓名
                    PlanBean planBean=new PlanBean(s,string);
                    MyApplication.mDBMaster.planDBDao.addSleep(planBean,user_name1,tt);
                    Toast.makeText(getContext(), "记录成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }





//    private int value(int oldValue,int newValue){
//        String oldValue1,newValue1;
//        oldValue1=String.valueOf(oldValue);
//        newValue1=String.valueOf(newValue);
//        int x=0;
//        if(oldValue1==null&&newValue1==null){
//            x=0;
//        }else if(newValue1==null){
//            x=oldValue;
//        }else if(oldValue1==null){
//            x=newValue;
//        }
//        return x;
//    }



























    /**
     * 计算时间差
     * @param hour
     * @param minute
     * @param hour1
     * @param minute1
     * @return
     */
    private String getTime(int hour,int minute,int hour1,int minute1){
        int x = 0,y = 0;
        if(minute>0){

           if(minute1-minute>=0){
               x=24-hour+hour1;
               y=minute1-minute;
           }else {
               x=23-hour+hour1;
               y=60-minute+minute1;
           }

        }


        if(minute==0){
            x=24-hour+hour1;
            y=0;

        }

        String x1=String.valueOf(x);

        String value=x1+"小时"+format(y)+"分钟";
        return value;

    }


    /**
     * 小于10，补个0
     * @param value
     * @return
     */
    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }















}
