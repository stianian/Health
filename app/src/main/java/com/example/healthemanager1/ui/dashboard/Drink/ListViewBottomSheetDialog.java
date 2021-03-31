package com.example.healthemanager1.ui.dashboard.Drink;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.util.DayUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ListViewBottomSheetDialog extends BottomSheetDialogFragment {

    private ListView drinkDbList;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private ListViewBottomAdapter listViewBottomAdapter;
    private String user_name1= MyApplication.name.getName() ;

    private String[] quantity;
    private String[] drinkName;
    private String[] drinkID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.da_drink_bd_list, container, false);
        drinkDbList = (ListView) view.findViewById(R.id.da_drink_ba_list);
        new Thread1().start();
        return view;
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



    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {

                        quantity=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),1);
                        drinkName=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),0);
                        drinkID=MyApplication.mDBMaster.drinkDBDao.getDrinkValue(user_name1,DayUtils.setDay(),2);
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

                    if (quantity.length == 0) {
//                        progressBar.setVisibility(View.GONE);
//                        tvDesc.setText("没有数据");
                    } else {
                        if (listViewBottomAdapter == null) { //第一次加载
                            listViewBottomAdapter = new ListViewBottomAdapter(getContext(),quantity,drinkName,drinkID);
                            drinkDbList.setAdapter(listViewBottomAdapter);
                        } else {
                            listViewBottomAdapter.notifyDataSetChanged();// 刷新listView 否则仍会从头开始 显
                        }
                        break;
                    }
            }
        }
    };

}
