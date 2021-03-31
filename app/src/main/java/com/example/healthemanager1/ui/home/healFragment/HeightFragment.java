package com.example.healthemanager1.ui.home.healFragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.model.HeightBean;
import com.example.healthemanager1.util.DayUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HeightFragment extends Fragment  {
    public LineChartView lineChart;
    private Button addheight;
    private ListView height_listview;
    private HeightAdapter heightAdapter;
    private List<HeightBean> heightBeanList;

    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private String[] height;

    private int totalcount =MyApplication.mDBMaster.heightDBDao.getHeightCount();
    private String[] name_num=new String[totalcount];
    private String user_name1= MyApplication.name.getName() ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fg_home_height,null);
        lineChart = (LineChartView) view.findViewById(R.id.line_chart);
        height_listview=(ListView)view.findViewById(R.id.heigh_listView);
        addheight=(Button)view.findViewById(R.id.add_height);
        initView();
        new Thread1().start();
        getAxisXLables();//获取x轴的标注
        return view;


    }

    private void initView(){
        addheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateNameDialog();
            }
        });

    }

    private void getAxisPoints() {
        for (int i = 0; i < height.length; i++) {
            mPointValues.add(new PointValue(i, Integer.valueOf(height[i])));
        }
    }

    private void getAxisXLables() {
        for (int i = 0; i <name_num.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(String.valueOf(i)));
        }
    }



    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {

                        height=MyApplication.mDBMaster.heightDBDao.getvalue(user_name1);
                        if(heightBeanList==null){
                            heightBeanList=MyApplication.mDBMaster.heightDBDao.getHeightNumByPage(user_name1);
                        }else{
                            heightBeanList.clear();
                            heightBeanList.addAll(MyApplication.mDBMaster.heightDBDao.getHeightNumByPage(user_name1));
                        }
                        handler.sendEmptyMessage(1);
                    } catch (Exception e) {
                    }

                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }

            }

        }

    }






    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://子线程获得了数据，开始刷新页面
                    DisplayChart();
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化

                    if (heightBeanList.size() == 0) {
//                        progressBar.setVisibility(View.GONE);
//                        tvDesc.setText("没有数据");
                    } else {

                        if (heightAdapter == null) { //第一次加载
                            heightAdapter=new HeightAdapter();
                            height_listview.setAdapter(heightAdapter);

                        } else {
                            heightAdapter.notifyDataSetChanged();// 刷新listView 否则仍会从头开始 显

                        }
                    }

                    break;
            }
        }

    };

    private void onCreateNameDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_h_height, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("请输入你的身高:");
        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.height_edit);
        // 设置Dialog按钮
        alertDialogBuilder.setCancelable(false).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String height=userInput.getText().toString();
                        HeightBean heightBean=new HeightBean(height, DayUtils.setDay());
                        MyApplication.mDBMaster.heightDBDao.addHeight(heightBean,user_name1);


                    }
                })
                .setNegativeButton("取消 ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





    private void initLineChart() {

        Line line = new Line(mPointValues).setColor(Color.parseColor("#03A9F4"));  //折线的颜色（
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setStrokeWidth(1);
        line.setFilled(true);//是否填充曲线的面积
        line.setColor(Color.GREEN);
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        //      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(R.color.s3);  //设置字体颜色

        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(totalcount); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部


        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限
        Axis axisY = new Axis();  //Y轴
        axisY.setName("身高");//y轴标注
        axisY.setTextSize(10);//设置字体大小


        float[] dataY = {150,155,160,165,170,175,180,185,190,195,200};//Y轴的标注
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < dataY.length; i++){
            AxisValue value = new AxisValue(dataY[i]);
            values.add(value);
        }

        axisY.setValues(values);

        axisY.setHasLines(true);

        data.setAxisYLeft(axisY);  //Y轴设置在左边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 1);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);




        Viewport v = new Viewport(0,201, totalcount, 154);
        if (v.top == v.bottom && v.top != 0) {   //解决最大值最小值相等时，图不能展示问题
            v.top = v.top * 2;
            v.bottom = 0;
        } else if (v.bottom == 0.0) {    //解决最大值最小值相等时全部为0时，图不能展示问题
            v.top = 1;
            v.bottom = 0;
        }

        lineChart.setMaximumViewport(v);
        v.left = 0;
        v.right = 10;
        lineChart.setCurrentViewport(v);

    }


    private void DisplayChart(){
        mPointValues.clear();
    }


    public class HeightAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return heightBeanList.size();
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
                convertView=View.inflate(getContext(), R.layout.item_home_list_height,null);
                vh=new ViewHolder();
                vh.title= (TextView) convertView.findViewById(R.id.height_value);
                vh.value=(TextView)convertView.findViewById(R.id.height_time);
                vh.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete_list_height);
                convertView.setTag(vh);
            }else {
                vh= (ViewHolder) convertView.getTag();
            }
            HeightBean heightBean = heightBeanList.get(position);

            vh.title.setText(heightBean.user_height+" cm");
            vh.value.setText(heightBean.height_time);
            vh.ivDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //从数据库中删除数据
                    MyApplication.mDBMaster.heightDBDao.deleteHeightNum(heightBeanList.get(position).user_height);
                    //从集合中删除数据
                    heightBeanList.remove(position);

                    //刷新页面
                    notifyDataSetChanged();
                }
            });
            return convertView;

        }

    }



    public static class ViewHolder{
         ImageView ivDelete;
        private TextView title,value;

    }


}