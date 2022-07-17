package com.mikel.projectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.mikel.projectdemo.uiframework.JetPackFragment;
import com.mikel.projectdemo.uiframework.MainFragment;
import com.mikel.projectdemo.uiframework.MainViewPagerAdapter;
import com.mikel.projectdemo.uiframework.MediaFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout mTabContainerLy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mViewPager = findViewById(R.id.view_pager);
        mTabContainerLy = findViewById(R.id.tab_container_ly);
        //adapter填充数据
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MainFragment.build());
        fragments.add(MediaFragment.build());
        fragments.add(JetPackFragment.build());
        int[] tabResArray = new int[]{R.layout.fragment_tab_main, R.layout.fragment_tab_media, R.layout.fragment_tab_jetpack};
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mViewPager, mTabContainerLy, tabResArray);
        mMainViewPagerAdapter.setFragmentList(fragments);
    }
}