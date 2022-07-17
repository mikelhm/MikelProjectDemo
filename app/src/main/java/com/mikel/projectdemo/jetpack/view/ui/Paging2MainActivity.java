package com.mikel.projectdemo.jetpack.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikel.projectdemo.R;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Paging2MainActivity extends AppCompatActivity{
    //本地数据源
    private TextView localTextView;
    //网络数据源
    private TextView networkTextView;
    //实现Tab滑动效果
    private ViewPager mViewPager;
    //游标
    private ImageView cursor;
    //游标偏移
    private int offset = 0;
    private int position_one;
    //游标宽度
    private int bmpW;
    //当前页卡编号
    private int currIndex = 0;

    //存放Fragment
    private ArrayList<Fragment> fragmentArrayList;
    //管理Fragment
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging2_main);
        initTextView();
        initImageView();
        initFragment();
        initViewPager();
    }

    private void initTextView() {
        localTextView = (TextView)findViewById(R.id.local_text);
        networkTextView =  (TextView) findViewById(R.id.network_text);
        localTextView.setOnClickListener(new CustomOnClickListener(0));
        networkTextView.setOnClickListener(new CustomOnClickListener(1));
    }

    private void initImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 获取分辨率宽度
        int screenW = dm.widthPixels;
        bmpW = (screenW/2);
        //设置游标的宽度
        setBmpW(cursor, bmpW);
        offset = 0;
        //动画图片偏移量赋值
        position_one = (int) (screenW / 2.0);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vPager);
        mViewPager.setAdapter(new CustomFragmentPagerAdapter(fragmentManager, fragmentArrayList));

        //让ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(2);

        //设置默认打开第一页
        mViewPager.setCurrentItem(0);

        //将顶部文字恢复默认值
        resetTextColor();
        localTextView.setTextColor(getResources().getColor(R.color.white));

        //设置viewpager页面滑动监听事件
        mViewPager.setOnPageChangeListener(new CustomOnPageChangeListener());
    }
    /**
     * 初始化Fragment，并添加到ArrayList中
     */
    private void initFragment(){
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new PoetryListFragmentForPaging2Local());
        fragmentArrayList.add(new PoetryListFragmentForPaging2Network());
        fragmentManager = getSupportFragmentManager();
    }

    public class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            Animation animation = null ;
            switch (position){
                case 0:
                    if(currIndex == 1){
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextColor();
                        localTextView.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;

                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextColor();
                        networkTextView.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
            }
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor.startAnimation(animation);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    /**
     * 设置游标宽度
     * @param mWidth
     */
    private void setBmpW(ImageView imageView,int mWidth){
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    /**
     * 将顶部文字恢复默认值
     */
    private void resetTextColor() {
        localTextView.setTextColor(getResources().getColor(R.color.light_green));
        networkTextView.setTextColor(getResources().getColor(R.color.light_green));
    }

    /**
     * 头标点击监听
     * @author weizhi
     * @version 1.0
     */
    public class CustomOnClickListener implements View.OnClickListener{
        private int index = 0 ;
        public CustomOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }


    public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentsList;
        public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentsList) {
            super(fm);
            this.fragmentsList = fragmentsList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    /**
     * 响应点击具体的item项
     * @param poetry
     */
    public void show(@NotNull Poetry poetry) {
        Intent intent = new Intent(this, PoetryDetailActivity.class);
        intent.putExtra("title", poetry.title);
        startActivity(intent);
    }
}
