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

    private TextView add_file,txt_username;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        add_file=(TextView)view.findViewById(R.id.add_file);
        txt_username=(TextView)view.findViewById(R.id.txt_username);
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

    }

}