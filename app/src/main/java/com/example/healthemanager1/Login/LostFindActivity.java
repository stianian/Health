package com.example.healthemanager1.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthemanager1.R;

public class LostFindActivity extends AppCompatActivity {
    private EditText et_lost_psw_again,et_lost_psw,et_lost_name;
    private String lostPsw,lostPswAgain,lostName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        init();
    }
    private void init() {

        //从activity_register.xml 页面中获取对应的UI控件
        Button btn_order = (Button) findViewById(R.id.btn_order);
        et_lost_psw= (EditText) findViewById(R.id.et_lost_psw);
        et_lost_psw_again= (EditText) findViewById(R.id.et_lost_psw_again);
        et_lost_name=(EditText)findViewById(R.id.et_lost_name);


        btn_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容

                if(TextUtils.isEmpty(lostName)){
                    Toast.makeText(LostFindActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(lostPsw)){
                    Toast.makeText(LostFindActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(lostPswAgain)) {
                    Toast.makeText(LostFindActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if(!lostPsw.equals(lostPswAgain)){
                    Toast.makeText(LostFindActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();

                    /**
                     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
                     */
//                }else if(isExistUserName(lostName)){
////                    Toast.makeText(LostFindActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
//
                }else{
                    Toast.makeText(LostFindActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    //把账号、密码和账号标识保存到sp里面
                    /**
                     * 保存账号和密码到SharedPreferences中
                     */
                    saveRegisterInfo(lostName, lostPsw);

                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", lostName);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    LostFindActivity.this.finish();
                }
            }
        });
    }


    private void getEditString(){
        lostName=et_lost_name.getText().toString().trim();
        lostPswAgain=et_lost_psw_again.getText().toString().trim();
        lostPsw=et_lost_psw.getText().toString().trim();

    }


    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }


    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);//把密码
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);

        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.apply();
    }
}
