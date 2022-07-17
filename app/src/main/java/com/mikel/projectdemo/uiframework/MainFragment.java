package com.mikel.projectdemo.uiframework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.uiframework.subtab.SubTabFragment1;
import com.mikel.projectdemo.uiframework.subtab.SubTabFragment2;
import com.mikel.projectdemo.uiframework.subtab.SubTabFragment3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainFragment extends Fragment {

    private TabSubViewPagerAdapter mTabSubViewPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout mTabContainerLy;


    public static MainFragment build() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null, true);
        mViewPager = rootView.findViewById(R.id.view_pager);
        mTabContainerLy = rootView.findViewById(R.id.tab_container_ly);
        //adapter填充数据
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SubTabFragment1.build());
        fragments.add(SubTabFragment2.build());
        fragments.add(SubTabFragment3.build());
        int[] tabResArray = new int[]{R.layout.fragment_sub_tab_title1, R.layout.fragment_sub_tab_title2, R.layout.fragment_sub_tab_title3};
        mTabSubViewPagerAdapter = new TabSubViewPagerAdapter(getChildFragmentManager(), mViewPager, mTabContainerLy, tabResArray);
        mTabSubViewPagerAdapter.setFragmentList(fragments);
        return rootView;
    }
}
