package com.mikel.projectdemo.uiframework;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikel.projectdemo.R;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class TabSubViewPagerAdapter extends BaseViewPagerAdapter {
    public TabSubViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, LinearLayout tabContainer, int[] tabResArray) {
        super(fragmentManager, viewPager, tabContainer, tabResArray);
    }


    @Override
    protected int getTabViewHeightDp() {
        return 30;
    }

    /**
     * 更新tab ui
     * @param position
     */
    @Override
    public void updateTabViewUI(int position) {
        for(int i =0; i < mTabViews.size(); i++) {
            View tabView = mTabViews.get(i);
            TextView tabTitleView = tabView.findViewById(R.id.tab_title);
            if(i == position) {
                tabTitleView.setTextColor(tabTitleView.getResources().getColor(R.color.white));
            } else {
                tabTitleView.setTextColor(tabTitleView.getResources().getColor(R.color.medium_green));
            }
        }
    }
}
