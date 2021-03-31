package com.example.healthemanager1.ui.dashboard.Diet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.healthemanager1.R;
import com.example.healthemanager1.ui.dashboard.Diet.widget.Node;
import com.example.healthemanager1.ui.dashboard.Diet.widget.OnTreeNodeCheckedChangeListener;
import com.example.healthemanager1.ui.dashboard.Diet.widget.TreeListViewAdapter;


import java.util.List;



public class ListViewAdapter extends TreeListViewAdapter {

    private OnTreeNodeCheckedChangeListener checkedChangeListener;
    public void setCheckedChangeListener(OnTreeNodeCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    public ListViewAdapter(ListView listView, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(listView, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
    }

    @Override
    public View getConvertView(final Node node, final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.da_diet_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(node.getName());
        holder.tvQuality.setText(node.getQuantity());
        holder.tvNameId.setText(node.getNameid());
        holder.imageView.setImageResource(node.getImage());
        if (node.getIcon() == -1) {
            holder.ivExpand.setVisibility(View.INVISIBLE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.ivExpand.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.ivExpand.setImageResource(node.getIcon());
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(node, holder.checkBox.isChecked());
                if (checkedChangeListener != null) {
                    checkedChangeListener.onCheckChange(node, position,holder.checkBox.isChecked());
                }
            }
        });

        if (node.isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        return convertView;
    }

    static class ViewHolder {
        private CheckBox checkBox;
        private TextView tvName,tvQuality,tvNameId;
        private ImageView ivExpand;
        private ImageView imageView;

        public ViewHolder(View convertView) {
            checkBox = convertView.findViewById(R.id.cb);
            tvName = convertView.findViewById(R.id.tv_name);
            ivExpand = convertView.findViewById(R.id.iv_expand);
            tvQuality = convertView.findViewById(R.id.tv_quality);
            tvNameId =convertView.findViewById(R.id.tv_name_id);
            imageView =convertView.findViewById(R.id.image1);


        }
    }
}
