package com.example.healthemanager1.ui.dashboard.Drink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthemanager1.R;
import com.example.healthemanager1.util.DayUtils;

import java.util.List;

public class GridViewBottomAdapter extends BaseAdapter {
    Context mContext;
    public GridViewBottomAdapter(Context context){
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return DayUtils.drink.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        convertView = layoutInflater.inflate(R.layout.item_da_drink_grid,null);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.drink_name);
        TextView textView = convertView.findViewById(R.id.textview_item);
        imageView.setImageResource(DayUtils.drink[position]);
        textView.setText(DayUtils.drinking[position]);
        return convertView;
    }




}
