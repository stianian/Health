package com.example.healthemanager1.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthemanager1.R;
import com.example.healthemanager1.ui.home.healFragment.HealthActivity;
import com.example.healthemanager1.application.MyApplication;

public class HomeFragment extends Fragment   {

    private HomeViewModel homeViewModel;

    private TextView name,height,weight,bmi,max_rata,static_rata;


    private Button more_such;


    private   String[] user_num;

    private Spinner spinner;

    private ArrayAdapter<String> adapter;
    private MyApplication allname;
    private static String spinnerValse="";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        more_such=(Button)view.findViewById(R.id.more_such);

        user_num=MyApplication.mDBMaster.userDBDao.getvalue();//获取用户姓名



        spinner = (Spinner)view.findViewById(R.id.sp);

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,user_num);//适配器


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉列表的风格

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE); //设置默认值

        more_such.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getActivity(), HealthActivity.class);
                startActivity(intent);
            }
        });


        name=(TextView)view.findViewById(R.id.name);

        height=(TextView)view.findViewById(R.id.height);
        weight=(TextView)view.findViewById(R.id.weight);
        bmi=(TextView)view.findViewById(R.id.BMI);
        max_rata=(TextView)view.findViewById(R.id.max_rate);
        static_rata=(TextView)view.findViewById(R.id.static_rate);


        return view;
    }









    /**
     * 页面每次切回来都会执行onCreateView，spinner的监听就会触发，值就会发生改变
     * static变量用来存放数值，只会初始化一次
     */

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //arg2为位置
            spinnerValse=user_num[arg2];
            name.setText(spinnerValse);
            String insert_name1=name.getText().toString();
            MyApplication.name.setName(spinnerValse);
            String[] arr=MyApplication.mDBMaster.heightDBDao.getvalue(insert_name1);//获取升高值

            String[] arr1=MyApplication.mDBMaster.weightDBDao.getvalue(insert_name1);

            String[] arr2=MyApplication.mDBMaster.maxRateDBDao.getvalue(insert_name1);

            String[] arr3=MyApplication.mDBMaster.staticDBDao.getvalue(insert_name1);




            if(insert_name1==null){
                return;
            }else {


                if(arr.length==0){
                    height.setText("暂无数据");
                }else {
                    height.setText(arr[arr.length-1]);
                }



                if(arr1.length==0){
                    weight.setText("暂无数据");
                }else {
                    weight.setText(arr1[arr1.length-1]);
                }


                if(arr1.length==0||arr.length==0){
                    bmi.setText("暂无数据");
                }else {
                    String b=BMI(arr1[arr1.length-1],arr[arr.length-1]);
                    bmi.setText(b);
                }

                if(arr2.length==0){
                    max_rata.setText("暂无数据");
                }else {
                    max_rata.setText(arr2[arr2.length-1]);
                }

                if(arr3.length==0){
                    static_rata.setText("暂无数据");
                }else {
                    static_rata.setText(arr3[arr3.length-1]);
                }



            }




        }


        public void onNothingSelected(AdapterView<?> arg0) {
            name.setText("还没有用户");
        }
    }


    /**
     * 计算bmi
     * @param wei_value
     * @param hei_value
     * @return
     */
    public String BMI(String wei_value,String hei_value){
        float x,y,bmi,dd;

        String string="暂无数据" ;
        x= Float.parseFloat(wei_value);
        y=Float.parseFloat(hei_value)/100;
        dd=x/(y*y);
        bmi = (float)(Math.round(dd*100))/100;//保留两位
        if(bmi<=18.4){
            string= bmi +"   偏瘦";
        }else if(bmi>=18.5&&bmi<=23.9){

            string=bmi+"   正常";

        }else if(bmi>=24&&bmi<=27.9){
            string =bmi+"   过重";

        }else if(bmi>=28){
            string =bmi+"   肥胖";
        }

        return string;
    }


}