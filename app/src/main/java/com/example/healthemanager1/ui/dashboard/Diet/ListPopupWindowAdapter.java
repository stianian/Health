package com.example.healthemanager1.ui.dashboard.Diet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.DietBean;
import com.example.healthemanager1.util.DayUtils;
import java.util.List;

public class ListPopupWindowAdapter extends BaseAdapter {
    private List<DietBean> dietBeanList;
    private Context mContext;
    public ListPopupWindowAdapter(List<DietBean> list, Context context) {
        super();
        this.dietBeanList = list;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return dietBeanList.size();
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.da_diet_popupview_item,null,false);
            vh=new ViewHolder();
            vh.tvName1= (TextView) convertView.findViewById(R.id.tv_name1);
            vh.tvQuality1=(TextView)convertView.findViewById(R.id.tv_quality1);
            vh.tvNameId1=(TextView) convertView.findViewById(R.id.tv_name_id1);
            vh.iv=(ImageView)convertView.findViewById(R.id.image2);
            vh.ivDelete=(ImageView)convertView.findViewById(R.id.iv_delete_list_po);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        DietBean dietBean = dietBeanList.get(position);
        vh.tvName1.setText(dietBean.dietName);
        vh.tvQuality1.setText(dietBean.quantity);
        int sort=Integer.parseInt(dietBean.nameID);
        int x=0;
        if(sort>0&&sort<6){
            x=sort-1;
            vh.tvNameId1.setText("??????");
            vh.iv.setImageResource(DayUtils.breakfirst2[x]);
        }else if(sort>=6&&sort<11){
            x=sort-6;
            vh.tvNameId1.setText("??????");
            vh.iv.setImageResource(DayUtils.lunch2[x]);
        }else if(sort>=11&&sort<16){
            x=sort-11;
            vh.tvNameId1.setText("??????");
            vh.iv.setImageResource(DayUtils.dinner2[x]);
        }
        vh.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.mDBMaster.dietDBDao.deleteDietNum(dietBeanList.get(position).dietName);//???????????????????????????
                dietBeanList.remove(position); //????????????????????????
                notifyDataSetChanged();//????????????
            }
        });

        return convertView;

    }


    public static class ViewHolder {
        private TextView tvName1, tvQuality1,tvNameId1;
        private ImageView iv,ivDelete;
    }



}




