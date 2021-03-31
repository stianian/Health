package com.example.healthemanager1.ui.dashboard.Diet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.healthemanager1.R;
import com.example.healthemanager1.application.MyApplication;
import com.example.healthemanager1.sql.DietBean;
import com.example.healthemanager1.ui.dashboard.Diet.widget.Node;
import com.example.healthemanager1.ui.dashboard.Diet.widget.NodeData;
import com.example.healthemanager1.ui.dashboard.Diet.widget.OnTreeNodeCheckedChangeListener;
import com.example.healthemanager1.util.DayUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 顺序只能这样，不明白
 * 会报错窗体泄露，解决方法 关掉，但还没有能够实现
 */
public class FManagerActivity extends AppCompatActivity {



    Context context;
    private ListView mListView;
    private List<Node> dataList = new ArrayList<>();
    private ListViewAdapter mAdapter;
    private ImageButton bt1;
    private Button btn;
    private ListPopupWindow mListPopupWindow;
    private ListPopupWindowAdapter mListPopupWindowAdapter;
    private List<DietBean> dietBeanList;
    private String user_name1= MyApplication.name.getName() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can);
        context=this;
        mListView = (ListView) findViewById(R.id.list1);
        bt1=(ImageButton)findViewById(R.id.bt_1);
        btn=(Button)findViewById(R.id.bt_2);
        initData();
        init();
        /**
         * 第一个参数  ListView
         * 第二个参数  上下文
         * 第三个参数  数据集
         * 第四个参数  默认展开层级数 0为不展开
         * 第五个参数  展开的图标
         * 第六个参数  闭合的图标
         */
        mAdapter = new ListViewAdapter(mListView, this, dataList, 0, R.mipmap.shiwu, R.mipmap.shiwu);
        mListView.setAdapter(mAdapter);
        //获取所有节点
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (Node allNode : allNodes) {
            //Log.e("xyh", "onCreate: " + allNode.getName());
        }

        //选中状态监听
        mAdapter.setCheckedChangeListener(new OnTreeNodeCheckedChangeListener() {
            @Override
            public void onCheckChange(Node node, int position, boolean isChecked) {
                //获取所有选中节点
                List<Node> selectedNode = mAdapter.getSelectedNode();
                if(isChecked){
                    showInfo(node.getName(),node.getQuantity(),node.getNameid());
                }
//                for (Node n : selectedNode) {
//                    System.out.println(n.getQuantity());
//                    Log.e("xyh", "onCheckChange: " + n.getQuantity());
//                }
            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopupWindow.setAnchorView(v);
                mListPopupWindow.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();  //直接关闭当前页面
            }
        });

    }



    private void init() {

        if(dietBeanList==null){
            dietBeanList=MyApplication.mDBMaster.dietDBDao.getDietNumByPage(user_name1, DayUtils.setDay());
        }else{
            dietBeanList.clear();
            dietBeanList.addAll(MyApplication.mDBMaster.dietDBDao.getDietNumByPage(user_name1, DayUtils.setDay()));
        }
        mListPopupWindow=new ListPopupWindow(context);
        mListPopupWindowAdapter=new ListPopupWindowAdapter(dietBeanList, context);
        mListPopupWindow.setAdapter(mListPopupWindowAdapter);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }



    /**
     * 模拟数据，实际开发中对返回的json数据进行封装
     */
    private void initData() {
        //根节点
        Node<NodeData> node = new Node<>("0", "-1", "早餐","","",0);
        dataList.add(node);
        dataList.add(new Node<>("1", "-1", "午餐","","",0));
        dataList.add(new Node<>("2","-1","晚餐","","",0));
        //根节点1的二级节点
        dataList.add(new Node<>("3", "0", DayUtils.breakfirst[0],DayUtils.breakfirst1[0],DayUtils.breakfirst0[0],DayUtils.breakfirst2[0]));
        dataList.add(new Node<>("4", "0", DayUtils.breakfirst[1],DayUtils.breakfirst1[1],DayUtils.breakfirst0[1],DayUtils.breakfirst2[1]));
        dataList.add(new Node<>("5", "0", DayUtils.breakfirst[2],DayUtils.breakfirst1[2],DayUtils.breakfirst0[2],DayUtils.breakfirst2[2]));
        dataList.add(new Node<>("6", "0", DayUtils.breakfirst[3],DayUtils.breakfirst1[3],DayUtils.breakfirst0[3],DayUtils.breakfirst2[3]));
        dataList.add(new Node<>("7", "0", DayUtils.breakfirst[4],DayUtils.breakfirst1[4],DayUtils.breakfirst0[4],DayUtils.breakfirst2[4]));
        //根节点2的二级节点
        dataList.add(new Node<>("8", "1", DayUtils.lunch[0],DayUtils.lunch1[0],DayUtils.lunch0[0],DayUtils.lunch2[0]));
        dataList.add(new Node<>("9", "1", DayUtils.lunch[1],DayUtils.lunch1[1],DayUtils.lunch0[1],DayUtils.lunch2[1]));
        dataList.add(new Node<>("10", "1", DayUtils.lunch[2],DayUtils.lunch1[2],DayUtils.lunch0[2],DayUtils.lunch2[2]));
        dataList.add(new Node<>("11", "1", DayUtils.lunch[3],DayUtils.lunch1[3],DayUtils.lunch0[3],DayUtils.lunch2[3]));
        dataList.add(new Node<>("12", "1", DayUtils.lunch[4],DayUtils.lunch1[4],DayUtils.lunch0[4],DayUtils.lunch2[4]));

        dataList.add(new Node<>("13", "2", DayUtils.dinner[0],DayUtils.dinner1[0],DayUtils.dinner0[0],DayUtils.dinner2[0]));
        dataList.add(new Node<>("14", "2", DayUtils.dinner[1],DayUtils.dinner1[1],DayUtils.dinner0[1],DayUtils.dinner2[1]));
        dataList.add(new Node<>("15", "2", DayUtils.dinner[2],DayUtils.dinner1[2],DayUtils.dinner0[2],DayUtils.dinner2[2]));
        dataList.add(new Node<>("16", "2", DayUtils.dinner[3],DayUtils.dinner1[3],DayUtils.dinner0[3],DayUtils.dinner2[3]));
        dataList.add(new Node<>("17", "2", DayUtils.dinner[4],DayUtils.dinner1[4],DayUtils.dinner0[4],DayUtils.dinner2[4]));



    }


    public void showInfo(String name,String quality,String nameID){
        FragmentDialog bottomSheetDialog = new FragmentDialog(name,quality,nameID);//这边传参数
        bottomSheetDialog.show(getSupportFragmentManager() ,"Dialog");
    }

}






