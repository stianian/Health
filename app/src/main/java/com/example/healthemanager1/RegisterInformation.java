package com.example.healthemanager1;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.model.UserBean;
import com.example.healthemanager1.ui.dashboard.DashboardFragment;
import com.example.healthemanager1.util.DayUtils;


public class RegisterInformation extends AppCompatActivity implements  View.OnClickListener {
    private String[] sexArry = new String[]{ "女", "男"};// 性别选择

    private Button name, sex, birth, age, save;
    private TextView changesex_textview,changename_textview,changebirth_textview,changeage_textview;
    private String tvName,tvSex,tvBirth,tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);

        name = (Button) findViewById(R.id.name_button);
        sex = (Button) findViewById(R.id.sex_button);
        age = (Button) findViewById(R.id.age_button);
        birth = (Button) findViewById(R.id.birth_button);


        save = (Button) findViewById(R.id.save);

        changename_textview=(TextView)findViewById(R.id.changename_textview);
        changesex_textview = (TextView) findViewById(R.id.changesex_textview);
        changeage_textview=(TextView)findViewById(R.id.changeage_textview);
        changebirth_textview=(TextView)findViewById(R.id.changebirth_textview);

        fillData();

        initview();
    }


    /**
     * 数据库不关闭了
     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 关闭数据库
//        MyApplication.mDBMaster.closeDataBase();
//    }

    private void initview() {


        name.setOnClickListener(this);
        birth.setOnClickListener(this);
        age.setOnClickListener(this);
        sex.setOnClickListener(this);
        save.setOnClickListener(this);

    }


    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            String days = new StringBuffer().append(mYear).append("年").append(mMonth+1).append("月").append(mDay).append("日").toString();
            changebirth_textview.setText(days);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birth_button:
//                showTimeChooseDialog();
//
                Calendar nowdate = Calendar.getInstance();
                final int mYear = nowdate.get(Calendar.YEAR);
                final int mMonth = nowdate.get(Calendar.MONTH);
                final int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
                //调用DatePickerDialog
                new DatePickerDialog(RegisterInformation.this, onDateSetListener, mYear-20, mMonth, mDay).show();
                break;
            case R.id.save:

               String username = changename_textview.getText().toString().trim();
                String user_sex = changesex_textview.getText().toString().trim();
                String user_age= changeage_textview.getText().toString().trim();
                String user_data=changebirth_textview.getText().toString().trim();
                UserBean userBean=new UserBean(username,user_sex,user_age,user_data);
                MyApplication.mDBMaster.userDBDao.addUser(userBean);

                break;
            case R.id.sex_button:
                showSexChooseDialog();
                break;
            case R.id.name_button:
                onCreateNameDialog();
                break;
            case R.id.age_button:
                onAge();
                break;
            default:
                break;

        }
    }




        private void fillData() {
            new Thread() {
                public void run() {
                    tvName=MyApplication.mDBMaster.userDBDao.getUser(0);
                    tvSex=MyApplication.mDBMaster.userDBDao.getUser(1);
                    tvAge=MyApplication.mDBMaster.userDBDao.getUser(2);
                    tvBirth=MyApplication.mDBMaster.userDBDao.getUser(3);

                    handler.sendEmptyMessage(1);
                }
            }.start();
        }




    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://子线程获得了数据，开始刷新页面

                    if(tvName==null){
                        changename_textview.setText("无");
                    }else {
                        changename_textview.setText(tvName);
                    }

                    if(tvSex==null){
                        changesex_textview.setText("无");
                    }else {
                        changesex_textview.setText(tvSex);
                    }
                    if(tvAge==null){
                        changeage_textview.setText("无");
                    }else {
                        changeage_textview.setText(tvAge);
                    }
                    if(tvBirth==null){
                        changebirth_textview.setText("无");
                    }else {
                        changebirth_textview.setText(tvBirth);
                    }



                    break;
            }
        }

    };







    private void onAge() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_age, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.changeage_edit);
//        final TextView age = (TextView) findViewById(R.id.changeage_textview);


        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                changeage_textview.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    private void showTimeChooseDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_time, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(nameView);
        final EditText time = (EditText) nameView.findViewById(R.id.changetime_edit);
        alertDialogBuilder.setCancelable(false).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                changebirth_textview.setText(time.getText());
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void onCreateNameDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.changename_edit);
//        final TextView name = (TextView) findViewById(R.id.changename_textview);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                changename_textview.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                changesex_textview.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }
}