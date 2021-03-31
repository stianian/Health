package com.example.healthemanager1.ui.dashboard.Drink;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.model.DrinkBean;
import com.example.healthemanager1.ui.dashboard.Drink.widget.CustomNumKeyViewThirsty;
import com.example.healthemanager1.util.DayUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GridViewBottomSheetDialog extends BottomSheetDialogFragment implements CustomNumKeyViewThirsty.CallBack {

    private String user_name1= MyApplication.name.getName() ;
    private GridView gridView;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private GridViewBottomAdapter gridPopupBottomAdapter;
    private EditText editText;
    private CustomNumKeyViewThirsty customNumKeyViewThirsty;
    private int position;
    private ImageView select;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.da_drink_bd_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);
        select=(ImageView)view.findViewById(R.id.selected_im);
        editText = (EditText) view.findViewById(R.id.ml);
        editText.setInputType(InputType.TYPE_NULL);// 设置不弹出系统键盘
        customNumKeyViewThirsty=(CustomNumKeyViewThirsty) view.findViewById(R.id.keyboardview1);
        customNumKeyViewThirsty.setOnCallBack(this);
        initViews();
        return view;
    }

    private void initViews() {

        gridPopupBottomAdapter=new GridViewBottomAdapter(getContext());
        gridView.setAdapter(gridPopupBottomAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewBottomSheetDialog.this.position=position;
                Toast.makeText(getContext(), DayUtils.drinking[position]+"", Toast.LENGTH_SHORT).show();
                select.setImageResource(DayUtils.drink[position]);
                return false;
            }


        });

    }
    /**
     * 一种可以拉起的弹窗模式
     */
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        final View view = getView();
        view.post(new Runnable() {
            @Override
            public void run() {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                mBottomSheetBehavior = (BottomSheetBehavior) behavior;
                mBottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
//              mBottomSheetBehavior.setPeekHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                parent.setBackgroundColor(Color.WHITE);
            }
        });

    }


    @Override
    public void clickNum(String num) {
        if (editText.getText().length() < 4) {
            editText.append(num);
        }
    }

    @Override
    public void deleteNum() {
        int last = editText.getText().length();
        if (last > 0) {
            //删除最后一位
            editText.getText().delete(last - 1, last);
        }
    }

    @Override
    public void deleteAllNum() {
        int last = editText.getText().length();
        if (last > 0) {
            editText.getText().delete(0 , last);
        }
    }

    @Override
    public void addNum() {
        String quantity=editText.getText().toString();
        String drinkName=DayUtils.drinking[position];
        String drinkKCal=DayUtils.drinkKCak[position];

        if(quantity.equals("")){
            Toast.makeText(getContext(), "记录失败，请输入饮水量", Toast.LENGTH_SHORT).show();
        }else {
            DrinkBean drinkBean=new DrinkBean(drinkName,quantity, DayUtils.setDay(),drinkKCal,String.valueOf(position));
            MyApplication.mDBMaster.drinkDBDao.addDrink(drinkBean,user_name1);
            Toast.makeText(getContext(), "已完成记录", Toast.LENGTH_SHORT).show();
        }

    }


}
