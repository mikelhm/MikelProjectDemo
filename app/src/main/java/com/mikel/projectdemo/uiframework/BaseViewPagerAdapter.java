package com.mikel.projectdemo.uiframework;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikel.baselib.utils.DisplayUtil;
import com.mikel.projectdemo.R;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BaseViewPagerAdapter extends FragmentPagerAdapter{
    protected ViewPager mViewPager;
    protected LinearLayout mTabContainer;
    protected int[] mTabResArray;
    protected List<Fragment> mFragmentList= new ArrayList<>();
    protected List<View> mTabViews = new ArrayList<>();

    public BaseViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, LinearLayout tabContainer, int[] tabResArray) {
        super(fragmentManager);
        this.mViewPager = viewPager;
        this.mTabContainer = tabContainer;
        this.mTabResArray = tabResArray;
        this.mViewPager.setAdapter(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTabViewUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void initTabView(int pos) {
        View singleTabView = LayoutInflater.from(mTabContainer.getContext()).inflate(mTabResArray[pos], mTabContainer, false);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)singleTabView.getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = DisplayUtil.dp2px(mTabContainer.getContext(), getTabViewHeightDp());
        layoutParams.weight = 1;
        mTabContainer.addView(singleTabView, layoutParams);
        GestureDetector gestureDetector = new GestureDetector(singleTabView.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                changeTabView(pos);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        singleTabView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        mTabViews.add(singleTabView);
    }

    protected int getTabViewHeightDp() {
        return 50;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 外部set fragment list
     * @param fragmentList
     */
    public void setFragmentList(List<Fragment> fragmentList) {
        this.mFragmentList.clear();
        this.mFragmentList.addAll(fragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mTabContainer.removeAllViews();
        this.mTabViews.clear();
        //初始化tab view
        for(int pos = 0; pos < mTabResArray.length; pos++) {
            initTabView(pos);
        }
        //更新tab view ui
        updateTabViewUI(0);
        //记得notify data
        notifyDataSetChanged();
    }

    /**
     * 切换tab
     * @param pos
     */
    protected void changeTabView(int pos) {
        mViewPager.setCurrentItem(pos);
    }


    /**
     * 更新tab ui
     * @param position
     */
    public void updateTabViewUI(int position) {
        for(int i =0; i < mTabViews.size(); i++) {
            View tabView = mTabViews.get(i);
            TextView tabTitleView = tabView.findViewById(R.id.tab_title);
            if(i == position) {
                tabTitleView.setTextColor(tabTitleView.getResources().getColor(R.color.white));
            } else {
                tabTitleView.setTextColor(tabTitleView.getResources().getColor(R.color.medium_orange));
            }
        }
    }
}
