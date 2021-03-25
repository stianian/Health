package com.example.healthemanager1.ui.dashboard.Drink;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthemanager1.R;
import com.example.healthemanager1.util.DayUtils;

public class ListViewBottomAdapter extends BaseAdapter {

    Context mContext;
    String[] quantity;
    String[] drinkName;
    String[] drinkID;
    public ListViewBottomAdapter(Context context,String[] quantity,String[] drinkName,String[] drinkID ){

        this.quantity=quantity;
        this.drinkName=drinkName;
        this.mContext = context;
        this.drinkID=drinkID;
    }
    @Override
    public int getCount() {
        return drinkName.length;
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
       ViewHolder vh=null;
        if(convertView==null){

            convertView=View.inflate(mContext, R.layout.item_da_drink_list,null);
            vh=new ViewHolder();
            vh.ivName=(ImageView)convertView.findViewById(R.id.iv_drink_db_list_item);
            vh.drinkTName=(TextView)convertView.findViewById(R.id.tv_drink_db_name);
            vh.drinkTWater=(TextView)convertView.findViewById(R.id.tv_drink_db_water);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.ivName.setImageResource(DayUtils.drink[Integer.parseInt(drinkID[position])]);
        vh.drinkTName.setText(drinkName[position]);
        vh.drinkTWater.setText(quantity[position]);

        return convertView;
    }



    public static class ViewHolder{
        ImageView ivName;
        TextView drinkTName,drinkTWater;
    }




}


