package com.example.healthemanager1.ui.dashboard.Drink;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthemanager1.R;
import com.example.healthemanager1.util.DayUtils;

public class DrinkTableActivity extends AppCompatActivity {
    private Context context;
    ListView drinkTableList;


    private TextView tip1,tip2;

    private TableListAdapter tableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_table);
        context=this;

       drinkTableList=(ListView)findViewById(R.id.drink_table_list);
       tip1=(TextView)findViewById(R.id.tip_1);
       tip2=(TextView)findViewById(R.id.tip_2);

       tip1.setText(DayUtils.tip1);
       tip2.setText(DayUtils.tip2);

       tableListAdapter=new TableListAdapter();
       drinkTableList.setAdapter(tableListAdapter);
    }




    public class TableListAdapter extends BaseAdapter{

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
            ViewHolder vh=null;
            if(convertView==null){

                convertView=View.inflate(context, R.layout.item_da_drink_table_list,null);
                vh=new ViewHolder();
                vh.ivName=(ImageView)convertView.findViewById(R.id.iv_drink_table);
                vh.drinkName=(TextView)convertView.findViewById(R.id.tv_drink_table_name);
                vh.drinkWater=(TextView)convertView.findViewById(R.id.tv_drink_table_water);
                vh.drinkKCal=(TextView)convertView.findViewById(R.id.tv_drink_table_kcal);
                convertView.setTag(vh);
            }else {
                vh= (ViewHolder) convertView.getTag();
            }
            vh.ivName.setImageResource(DayUtils.drink[position]);
            vh.drinkName.setText(DayUtils.drinking[position]);
            vh.drinkWater.setText(DayUtils.drinkWater[position]);
            vh.drinkKCal.setText(DayUtils.drinkKCak[position]);
            return convertView;
        }
    }



    public static class ViewHolder{
        ImageView ivName;
        TextView drinkName,drinkWater,drinkKCal;
    }

}
