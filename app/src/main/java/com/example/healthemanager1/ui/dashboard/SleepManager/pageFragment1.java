package com.example.healthemanager1.ui.dashboard.SleepManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.model.PlanBean;
import com.example.healthemanager1.ui.notifications.NotificationsViewModel;
import com.example.healthemanager1.ui.dashboard.SleepManager.widget.CustomFragmentDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;

public  class pageFragment1 extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private int year,month,day ;
    private TextView re1,re2;
    private Button add_sleep,add_r;
    private String s1;//日期
    private List<PlanBean> planBeans;
    private String userName= MyApplication.name.getName();

    /**
     * 时间选择
     */
    DatePicker datePicker;
    /**
     * 日期
     */
    String year1,month1,day1;


    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fg_da_sleep_record, container, false);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker1);
        add_sleep=(Button)view.findViewById(R.id.add_sleep);
        add_r=(Button)view.findViewById(R.id.add_r);
        re1=(TextView)view.findViewById(R.id.re1);
        re2=(TextView)view.findViewById(R.id.re2);


        /**
         * 当前年月日
         */
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        init();
        return view;


    }

    private void init(){
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                pageFragment1.this.year = year;
                pageFragment1.this.month = month;
                pageFragment1.this.day = day;

                showToday(year,month,day);


            }
        });

        add_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year1=String.valueOf(year);
                month1=String.valueOf(month);
                day1=String.valueOf(day);
                if(month>calendar.get(Calendar.MONTH)){
                    Toast.makeText(getContext(), "不能记录未来的日子", Toast.LENGTH_SHORT).show();
                }else if(month==calendar.get(Calendar.MONTH)){
                    if(day>calendar.get(Calendar.DAY_OF_MONTH)){
                        Toast.makeText(getContext(), "不能记录未来的日子", Toast.LENGTH_SHORT).show();
                    }else {
                        showInfo(year1,month1,day1);
                    }
                }else {
                    showInfo(year1,month1,day1);
                }



            }
        });



        add_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year1=String.valueOf(year);
                s1 = year1+"-"+format(month+1)+"-"+format(day);
                planBeans= MyApplication.mDBMaster.planDBDao.getPlanByPage(userName,s1);//存入数据库
                if(planBeans.size()==0){
                    re1.setText("暂无数据");
                    re2.setText("暂无数据");

                }else {
                    PlanBean planBean=planBeans.get(0);
                    re1.setText(planBean.sleep);//时间段
                    re2.setText(planBean.sleep_time);//时长
                }


            }
        });
    }







    /**
     * 开启时间选择器
     * @param x 年
     * @param y 月
     * @param z 日
     */
    public void showInfo(String x,String y,String z){

        CustomFragmentDialog bottomSheetDialog = new CustomFragmentDialog(x,y,z);//这边传参数
        bottomSheetDialog.show(getActivity().getSupportFragmentManager() ,"Dialog");//
    }


    /**
     * 显示一下今天的日期
     * @param x 年
     * @param y 月
     * @param z 日
     */
    public void showToday(int x,int y,int z){
        String YEAR=String.valueOf(x);
        s1 = YEAR+"-"+format(y+1)+"-"+format(z);
        Toast.makeText(getContext(), s1, Toast.LENGTH_SHORT).show();
    }

    /**
     * 小于10，补个0
     * @param value
     * @return
     */

    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }












}
