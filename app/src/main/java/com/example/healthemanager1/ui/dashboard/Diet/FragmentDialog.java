package com.example.healthemanager1.ui.dashboard.Diet;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.DietBean;
import com.example.healthemanager1.util.DayUtils;
import com.example.healthemanager1.widget.CustomNumKeyView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentDialog extends BottomSheetDialogFragment implements CustomNumKeyView.CallBack{


    private TextView allQuantity;
    private Button psy_money;
    private TextView count;
    private String name,quality,nameId;
    private String user_name1= MyApplication.name.getName() ;
    private  TextView dialogName,dialogQuality;
    ImageView image;
    private CustomNumKeyView customNumKeyView;
    private EditText edit;

    String allQuality;
    public FragmentDialog(String name,String quality,String nameId) {


        this.name=name;
        this.quality=quality;
        this.nameId=nameId;



    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.da_diet_bottomdialog,container, false);
        psy_money=(Button)view.findViewById(R.id.pay_money);

        dialogName=(TextView)view.findViewById(R.id.dialog_name);
        dialogQuality=(TextView)view.findViewById(R.id.dialog_quality);
        allQuantity=(TextView)view.findViewById(R.id.allquantity);
        count=(TextView)view.findViewById(R.id.etxt_num1);
        image=(ImageView)view.findViewById(R.id.image1);

        edit=(EditText) view.findViewById(R.id.edit);
        edit.setInputType(InputType.TYPE_NULL);// 设置不弹出系统键盘
        customNumKeyView=(CustomNumKeyView)view.findViewById(R.id.keyboardview);
        customNumKeyView.setOnCallBack(this);

        initViews(view);

        return view;
    }

    private void initViews(View view) {





        dialogName.setText(name);

        dialogQuality.setText(quality);



        int NameID=Integer.parseInt(nameId);
        if(NameID>=1&&NameID<=5){
            image.setBackgroundResource(DayUtils.breakfirst2[NameID-1]);
        }else if(NameID>=6&&NameID<=10){
            image.setBackgroundResource(DayUtils.lunch2[NameID-6]);
        }else if(NameID>=10&&NameID<=15){
            image.setBackgroundResource(DayUtils.dinner2[NameID-11]);
        }


        psy_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affirm();



            }
        });
    }

    private void showTotalValue(int value) {
        int total =0;
        total=value*Integer.parseInt(quality);
        allQuantity.setText( String.valueOf(total) );
    }




    private void affirm() {
        allQuality=allQuantity.getText().toString();//
        DietBean dietBean =new DietBean(name,allQuality,nameId);

        MyApplication.mDBMaster.dietDBDao.addDiet(dietBean,user_name1, DayUtils.setDay());



    }




    @Override
    public void clickNum(String num) {

        if (edit.getText().length() < 2) {
            edit.append(num);
        }
        int value=Integer.parseInt(edit.getText().toString());
        showTotalValue(value);
    }

    @Override
    public void deleteNum() {
        int last = edit.getText().length();
        if (last > 0) {
            //删除最后一位
            edit.getText().delete(last - 1, last);
        }
        int value=Integer.parseInt(edit.getText().toString());
        showTotalValue(value);
    }

}



