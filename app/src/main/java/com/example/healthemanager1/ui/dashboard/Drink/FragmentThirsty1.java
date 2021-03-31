package com.example.healthemanager1.ui.dashboard.Drink;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.util.DayUtils;

import java.util.HashSet;

public class FragmentThirsty1 extends Fragment {
    private String user_name1= MyApplication.name.getName() ;


    private ImageView iv1,ivTarget,ivDelete,ivTable,ivDrinkList;

    private String drinkValue;
    private TextView targetNum,todayValue,todayKValue,todayWValue,totalDay;
    float totalQuantity=0,totalKCal=0;
    int totalDayNum=0;
    private String[] quantity;
    private String[] drinkName;
    private String[] data;
    private String dQuantity;
    private String dDrinkName;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_da_drink_record, container, false);

        iv1=(ImageView)view.findViewById(R.id.iv_pop);
        ivDelete=(ImageView)view.findViewById(R.id.drink_delete);
        ivTarget=(ImageView)view.findViewById(R.id.drink_target);
        ivDrinkList=(ImageView)view.findViewById(R.id.drink_list);
        ivTable=(ImageView)view.findViewById(R.id.drink_table);

        targetNum=(TextView)view.findViewById(R.id.target_number);
        todayValue=(TextView)view.findViewById(R.id.today_value);
        todayKValue=(TextView)view.findViewById(R.id.drink_water);
        todayWValue=(TextView)view.findViewById(R.id.drink_cal);
        totalDay=(TextView)view.findViewById(R.id.tv_total_day);
        new Thread1().start();
        init();

        return view;


    }
    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {

                        totalQuantity=MyApplication.mDBMaster.drinkDBDao.getvalue(user_name1, DayUtils.setDay(),0);
                        totalKCal=MyApplication.mDBMaster.drinkDBDao.getvalue(user_name1, DayUtils.setDay(),1);
                        quantity=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),1);
                        drinkName=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),0);
                        data=MyApplication.mDBMaster.drinkDBDao.getDrinkData(user_name1);
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
                    todayValue.setText(totalQuantity+"ml");
                    todayKValue.setText(totalKCal+"kcal");
                    todayWValue.setText(totalQuantity+"ml");
                    if(quantity.length==0||drinkName.length==0){
                        dQuantity="0";
                        dDrinkName="0";
                    }else {
                        int qLength=0;
                        qLength=quantity.length;
                        HashSet   hs   =   new HashSet();
                        for (int i=0;i<data.length;i++){

                            hs.add(data[i]);
                        }
                        totalDayNum=hs.size();

                        totalDay.setText(totalDayNum+" 天");
                        dQuantity=quantity[qLength-1];
                        dDrinkName=drinkName[qLength-1];
                    }



                    break;
            }
        }


    };


    private void init(){
        targetNum.setText(readValue()+"ml");

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        ivTarget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onCreateNameDialog();
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });


        ivTable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DrinkTableActivity.class);
                startActivity(intent);
            }
        });

        ivDrinkList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showListInfo();
            }
        });



    }


    private void onCreateNameDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.da_thirsty_target, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("请输入你今日喝水目标:");
        alertDialogBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.drink_target_dialog);
        alertDialogBuilder.setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        drinkValue=userInput.getText().toString();
                        saveValueInfo(drinkValue);


                    }
                })
                .setNegativeButton("取消 ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
        normalDialog.setTitle("撤销喝水记录");
        normalDialog.setMessage("确认要撤销"+dQuantity+"ml "+dDrinkName +"的记录吗？");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       MyApplication.mDBMaster.drinkDBDao.deleteDrinkNum(dDrinkName);
                    }
                });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // 显示
        normalDialog.show();
    }

    public void showInfo(){
        GridViewBottomSheetDialog bottomSheetDialog = new GridViewBottomSheetDialog();//这边传参数
        bottomSheetDialog.show(getFragmentManager() ,"Dialog");
    }

    public void showListInfo(){
        ListViewBottomSheetDialog bottomSheetDialog = new ListViewBottomSheetDialog();//这边传参数
        bottomSheetDialog.show(getFragmentManager() ,"Dialog");
    }


    /**
     * 存
     * @param value
     */
    private void saveValueInfo(String value){
        SharedPreferences sp=getActivity().getSharedPreferences("drink", Activity.MODE_PRIVATE);
       SharedPreferences.Editor editor=sp.edit();
        editor.putString("drinkValue", value);
        editor.apply();
    }

    /**
     * 取
     * @return
     */
    private String readValue(){
        SharedPreferences sp=getActivity().getSharedPreferences("drink",Activity.MODE_PRIVATE);
        return sp.getString("drinkValue" , "");
    }

}
