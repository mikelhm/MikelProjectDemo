package com.mikel.projectdemo.uiframework;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikel.projectdemo.R;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

/**
 * MainActivity ViewPager的 Adapter
 */
public class MainViewPagerAdapter extends BaseViewPagerAdapter {
    public MainViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, LinearLayout tabContainer, int[] tabResArray) {
        super(fragmentManager, viewPager, tabContainer, tabResArray);
    }


}
