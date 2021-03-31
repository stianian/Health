package com.example.healthemanager1.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthemanager1.ui.dashboard.SleepManager.SleepActivity;
import com.example.healthemanager1.ui.dashboard.Diet.FManagerActivity;
import com.example.healthemanager1.ui.dashboard.Drink.ThirstyActivity;
import com.example.healthemanager1.ui.dashboard.widget.CircleProgress1View;
import com.example.healthemanager1.util.DayUtils;
import com.example.healthemanager1.widget.NumberPickerDialog;
import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView ex_1,ex_2;
    private Button add_tar;
    private TextView rest,rest_time;
    private TextView score3,score2,score1,score4;
    private TextView drink1,drink2;
    private TextView diet1,diet2;
    private String user_name1= MyApplication.name.getName() ;
    private String step=MyApplication.step.getStep();
    private String[] height=null;
    private String weight[]=null;
    private String[] arr=null;
    private String[] arr1=null;
    private  String str="6000";
    private String km;
    private String kk;
    private String drinkTarget;
    float drinkNum=0;
    private String[] breakFirst;
    private CircleProgress1View circleProgress1View,circleBreakFirst;
    private CardView card3,card2,card4;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ex_1=(TextView)root.findViewById(R.id.ex_1);
        ex_2=(TextView)root.findViewById(R.id.ex_2);

        rest=(TextView)root.findViewById(R.id.rest);
        rest_time=(TextView)root.findViewById(R.id.rest_time);

        diet1=(TextView)root.findViewById(R.id.deit1);
        diet2=(TextView)root.findViewById(R.id.deit2);

        drink1=(TextView)root.findViewById(R.id.drink1);
        drink2=(TextView)root.findViewById(R.id.drink2);

        score2=(TextView)root.findViewById(R.id.score2);
        score3=(TextView)root.findViewById(R.id.score3);
        score1=(TextView)root.findViewById(R.id.score1);
        score4=(TextView)root.findViewById(R.id.score4);

        card3=(CardView)root.findViewById(R.id.card3);
        card2=(CardView)root.findViewById(R.id.card2);
        card4=(CardView)root.findViewById(R.id.card4);

        add_tar=(Button)root.findViewById(R.id.add_tar);

        circleProgress1View = (CircleProgress1View) root.findViewById(R.id.circleProgressView1);
        circleBreakFirst =(CircleProgress1View)root.findViewById(R.id.circleProgressView2) ;
        new Thread1().start();
        initview();
        return root;
    }


    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        drinkTarget=readValue();
                        drinkNum=MyApplication.mDBMaster.drinkDBDao.getvalue(user_name1,DayUtils.setDay(),0);
                        height=MyApplication.mDBMaster.heightDBDao.getvalue(user_name1);
                        weight=MyApplication.mDBMaster.weightDBDao.getvalue(user_name1);
                        arr= MyApplication.mDBMaster.planDBDao.getvalue1(DayUtils.setDay(),user_name1,0);
                        arr1=MyApplication.mDBMaster.planDBDao.getvalue1(DayUtils.setDay(),user_name1,1);
                        str=MyApplication.mDBMaster.planDBDao.getvalue(DayUtils.setDay());//步数,今日设置的
                        breakFirst=MyApplication.mDBMaster.dietDBDao.getDietValue1(user_name1,DayUtils.setDay());
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
                case 1:
                    /**
                     * 运动消耗
                     */
                    if(height.length>0&&weight.length>0){
                        km=km(step,height[height.length-1]);
                        kk=kk(step,weight[weight.length-1],height[height.length-1]);
                        ex_1.setText(step);//今日步数
                        ex_2.setText("今日运动了"+km+"公里,消耗了"+kk+"千卡能量");
                    }else {

                        ex_1.setText(step);
                        ex_2.setText("暂无数据");
                    }


                    /**
                     * 饮食
                     */
                    if(breakFirst.length==0){
                        score1.setText("没有数据");
                        circleBreakFirst.setProgress(0,0);//百分比图
                    }else {


                        float dietValue=0;
                        for(int i=0;i<breakFirst.length;i++){
                            dietValue+=Float.parseFloat(breakFirst[i]);
                        }
                        diet1.setText(dietValue+"");//
                        diet2.setText(kk+"");//运动消耗

                        float dietProgress = (float)(Math.round(dietValue/1911*10000))/100;//保留两位
                        int dietProgressScore=(int) dietValue*100/1911;
                        if(dietProgressScore>100){
                            circleBreakFirst.setProgress(100,1911);
                            score1.setText(String.format("完成目标，%d分",100));//饮食分数
                        }else {
                            circleBreakFirst.setProgress(dietProgress,1911);
                            score1.setText(String.format("未完成目标，%d分",dietProgressScore));//饮食分数

                        }

                    }


                    /**
                     * 饮水
                     */
                    if(arr.length==0){
                        rest_time.setText("没有数据");
                    }else {


                        rest_time.setText(arr[0]);
                    }


                    if(drinkTarget==""){
                        drink1.setText("还没有目标");
                        score4.setText("没有数据");
                    }else {
                        drink1.setText(drinkTarget+" ml");//饮水目标
                        int drinkScore=(int)drinkNum*100/Integer.parseInt(drinkTarget);
                        if(drinkScore>100){
                            score4.setText(String.format("完成目标，%d分",100));
                        }else {
                            score4.setText(String.format("未完成目标，%d分",drinkScore));
                        }

                    }
                    drink2.setText(drinkNum+" ml");//今日饮水量


                    /**
                     * 睡眠
                     */
                    if(arr1.length==0){

                        rest.setText("没有数据");
                    }else {
                        char first = arr1[0].charAt(0);
                        int r_time=Integer.parseInt(String.valueOf(first));
                        if(r_time>=7){
                            score3.setText("睡眠充足，100分");
                        }else if(r_time<7){
                            int restScore=r_time*100/7;
                            score3.setText(String.format("未完成目标，%d分",restScore));//睡眠分数
                        }
                        rest.setText(arr1[0]);//睡眠时长
                    }


                    /**
                     * 运动
                     */
                    if(str == null){
                       circleProgress1View.setProgress(0,0);

                    }else {
                        int x=Integer.parseInt(step);
                        int y=Integer.parseInt(str);
                        int dou=x*100/(y+1);

                        if(x<y){
//                            ex_3.setText(String.format("今日目标%d步，距离达成目标还差%d步",y,y-x));
                            score2.setText(String.format("未完成目标，%d分",dou));
                            circleProgress1View.setProgress(dou,y);
                        }else {
//                            ex_3.setText("今日目标已完成");
                            circleProgress1View.setProgress(100,y);
                            score2.setText(String.format("完成目标，%d分",100));

                        }


                    }

                    break;
            }
        }

    };







    private void initview(){

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ThirstyActivity.class);
                startActivity(intent);
            }
        });


        add_tar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new NumberPickerDialog(getContext(), new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                String newValue = String.valueOf(newVal);
                                MyApplication.mDBMaster.planDBDao.addStep(newValue,DayUtils.setDay());

                            }
                        },
                        10000, // 最大值
                        2000, // 最小值
                        6000

                ) // 默认值

                        .setCurrentValue(6000) // 更新默认值
                        .show();

            }
        });


        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent =new Intent(getActivity(), FManagerActivity.class);
                startActivity(intent);
            }
        });


        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), SleepActivity.class);
                startActivity(intent);
            }
        });

    }



    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }


    /**
     * 路程
     * @param step
     * @param height
     * @return
     */
    public String km(String step,String height){
        float x=Float.parseFloat(step);
        float y= (float) (Float.parseFloat(height)*0.145);
        float z=x*y/100000;
        float dd = (float)(Math.round(z*100))/100;//保留两位
        String k=String.valueOf(dd);
        return k;


    }


    /**
     * 能量
     * @param step
     * @param weight
     * @param height
     * @return
     */
    public String kk(String step,String weight,String height){
        float x=Float.parseFloat(step);
        float y=Float.parseFloat(weight);
        float z=Float.parseFloat(height);
        float kk= (float) (x*y*z*0.145*0.8214/100000);
        float dd = (float)(Math.round(kk*100))/100;//保留两位
        String k=String.valueOf(dd);
        return k;


    }


    /**
     * 读取数据，饮水目标
     * @return
     */
    private String readValue(){
        SharedPreferences sp=getActivity().getSharedPreferences("drink", Activity.MODE_PRIVATE);
        return sp.getString("drinkValue" , "");
    }
    




}