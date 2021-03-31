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
import com.example.healthemanager1.sql.model.StaticBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class StaticRataFragment extends Fragment {

    public LineChartView lineChart;
    private Button add_static;
    private ListView static_listview;
    private  StaticAdapter staticAdapter;
    private List<StaticBean> staticBeans;
    private String[] sta;
    private int totalcount = MyApplication.mDBMaster.staticDBDao.getStaCount();
    private String[] name_num=new String[totalcount];
    private String user_name1= MyApplication.name.getName() ;
    public List<PointValue> mPointValues = new ArrayList<PointValue>();
    public List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fg_home_static,null);
        lineChart = (LineChartView) view.findViewById(R.id.line_chart_static);
        static_listview=(ListView)view.findViewById(R.id.static_listView);
        add_static=(Button)view.findViewById(R.id.add_static);

        new Thread1().start();
        getAxisXLables();//获取x轴的标注

        initView();
        return view;


    }



    private void initView(){

        add_static.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateNameDialog();

            }
        });



    }


    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {

                        sta=MyApplication.mDBMaster.staticDBDao.getvalue(user_name1);
                        if(staticBeans==null){
                            staticBeans=MyApplication.mDBMaster.staticDBDao.getStaticNumByPage(user_name1);

                        }else{
                            staticBeans.clear();
                            staticBeans.addAll(MyApplication.mDBMaster.staticDBDao.getStaticNumByPage(user_name1));
                        }

                        handler1.sendEmptyMessage(1);
                    } catch (Exception e) {
                    }

                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }

            }

        }

    }






    private Handler handler1 = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1://子线程获得了数据，开始刷新页面

                    mPointValues.clear();
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化
                    if (staticBeans.size() == 0) {// 没有黑名单的情况
                    } else {
                        if (staticAdapter == null) { //第一次加载
                            staticAdapter=new StaticRataFragment.StaticAdapter();
                            static_listview.setAdapter(staticAdapter);

                        } else {
                            //追加数据
                            staticAdapter.notifyDataSetChanged();// 刷新listView 否则仍会从头开始 显示

                        }
                    }






                    break;
            }
        }

    };







    private void onCreateNameDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_h_static, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("请选择");
        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.static_edit);

        // 设置Dialog按钮
        alertDialogBuilder.setCancelable(false).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String sta=userInput.getText().toString();


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                        Date date = new Date();
                        String s = simpleDateFormat.format(date);
                        StaticBean staticBean=new StaticBean(sta,s);
                        MyApplication.mDBMaster.staticDBDao.addsta(staticBean,user_name1);
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





    private void getAxisPoints() {
        for (int i = 0; i < sta.length; i++) {
            mPointValues.add(new PointValue(i, Integer.valueOf(sta[i])));

        }
    }

    private void getAxisXLables() {
        for (int i = 0; i <name_num.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(String.valueOf(i)));
        }
    }






    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#03A9F4"));  //折线的颜色（
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(true);//是否填充曲线的面积
        line.setColor(Color.GREEN);
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setStrokeWidth(1);
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(R.color.teal_700);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(totalcount); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(false); //x 轴分割线
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        axisY.setHasLines(true);
        float[] dataY = {55,60,65,70,75,80,85,90,95,100,105};//Y轴的标注
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < dataY.length; i++){
            AxisValue value = new AxisValue(dataY[i]);
            values.add(value);
        }
        axisY.setValues(values);

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 1);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(0,106, totalcount, 54);

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




    public class StaticAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return staticBeans.size();
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
            StaticRataFragment.ViewHolder vh=null;


            if(convertView==null){
                convertView=View.inflate(getContext(), R.layout.item_home_list_static,null);

                vh=new StaticRataFragment.ViewHolder();
                vh.title= (TextView) convertView.findViewById(R.id.static_time);
                vh.value=(TextView)convertView.findViewById(R.id.static_value);
                vh.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete_list_static);
                convertView.setTag(vh);
            }else {
                vh= (StaticRataFragment.ViewHolder) convertView.getTag();
            }
            StaticBean staticBean = staticBeans.get(position);

            vh.title.setText(staticBean.StaticRate_time);
            vh.value.setText(staticBean.User_StaticRate+" bmp");

            vh.ivDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //从数据库中删除数据
                    MyApplication.mDBMaster.staticDBDao.deleteStaticNum(staticBeans.get(position).User_StaticRate);
                    //从集合中删除数据
                    staticBeans.remove(position);
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
