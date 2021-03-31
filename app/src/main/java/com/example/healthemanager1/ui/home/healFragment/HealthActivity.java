package com.example.healthemanager1.ui.home.healFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.healthemanager1.R;
import com.example.healthemanager1.ui.home.healFragment.widget.CategoryTabStrip;

import java.util.ArrayList;
import java.util.List;

public class HealthActivity extends AppCompatActivity {
    private CategoryTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private ArrayList<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heal);
        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        pager = (ViewPager) findViewById(R.id.view_pager);
        fragments=new ArrayList<>();
        HeightFragment heightFragment =new HeightFragment();
        WeightFragment newsFragment=new WeightFragment();
        MaxrataFragment MaxrataFragment =new MaxrataFragment();
        StaticRataFragment staticRataFragment =new StaticRataFragment();

        fragments.add(heightFragment);
        fragments.add(newsFragment);
        fragments.add(staticRataFragment);
        fragments.add(MaxrataFragment);

        FragmentManager fragmentManager=getSupportFragmentManager();
        adapter = new MyPagerAdapter(fragmentManager,fragments);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<String>();
        ArrayList<Fragment>fragments;


        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);

            this.fragments=fragments;
            catalogs.add("身高");
            catalogs.add("体重");
            catalogs.add("静息心率");
            catalogs.add("最大心率");

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }

}
