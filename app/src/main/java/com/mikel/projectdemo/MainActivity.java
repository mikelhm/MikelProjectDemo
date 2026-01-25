package com.mikel.projectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;
import com.hjq.permissions.XXPermissions;
import com.hjq.permissions.permission.PermissionLists;
import com.mikel.projectdemo.audio.PlayerUtil;
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
        enableFullScreen();
        initUI();
        XXPermissions.with(this)
          .permission(PermissionLists.getManageExternalStoragePermission())
          .request((grantedList, deniedList) -> {
              boolean allGranted = deniedList.isEmpty();
              if (!allGranted) {
                  boolean doNotAskAgain = XXPermissions.isDoNotAskAgainPermissions(MainActivity.this, deniedList);
              }
          });
        PlayerUtil.INSTANCE.createNotificationChannel(this.getApplicationContext());
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

    private void enableFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (API 30) 及以上
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Android 4.4 (API 19) 及以上
            int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_FULLSCREEN;

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);

            // 设置状态栏透明
            getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
            getWindow().setNavigationBarColor(android.graphics.Color.TRANSPARENT);
        }
    }
}