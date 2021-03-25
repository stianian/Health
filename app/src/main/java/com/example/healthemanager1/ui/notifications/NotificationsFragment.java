package com.example.healthemanager1.ui.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthemanager1.R;
import com.example.healthemanager1.RegisterInformation;
import com.example.healthemanager1.application.MyApplication;

public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;

    private TextView add_file,mf_data,txt_username,add_file1;
    private Button open;

    private Cursor mCursor;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

//        mf_data=(TextView)view.findViewById(R.id.mf_data);
        add_file=(TextView)view.findViewById(R.id.add_file);
//        open=(Button)view.findViewById(R.id.open);
        txt_username=(TextView)view.findViewById(R.id.txt_username);
//        add_file1=(TextView)view.findViewById(R.id.add_file1);

        initView();


        return view;
    }


    private void initView() {


        txt_username.setText(MyApplication.name.getName());

        add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RegisterInformation.class);
                startActivity(intent);
            }
        });


//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                StringBuilder recvText = new StringBuilder();
//
//                mCursor = MyApplication.mDBMaster.dietDBDao.rawQuery("select * from Diet_table ", null);
//
//                while(mCursor.moveToNext()){// cursor.moveToNext() 向下移动一行,如果有内容，返回true
//                    int nameColumnIndex = mCursor.getColumnIndex("name_id");
//                    String[] myFloats = new String[mCursor.getCount()];
//                    for (int i = 0; i < mCursor.getCount(); i++)
//                    {
//                        myFloats[i] = mCursor.getString(nameColumnIndex);
//                        mCursor.moveToNext();
//                        System.out.println(myFloats[i]);
//                        recvText.append("\r\n"+myFloats[i]);
//                    }
//
//                    System.out.println(recvText);
//                    mf_data.setText("接收数据"+"\r\n"+recvText);
//
//                }
//
//                mCursor.close();
//            }
//        });
//








    }

}