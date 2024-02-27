package com.mikel.projectdemo.uiframework;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.google.android.exoplayer2.ui.PlayerView;
import com.mikel.baselib.utils.AppUtil;
import com.mikel.projectdemo.R;
import com.mikel.projectdemo.media.video.VideoPlayManager;
import com.mikel.projectdemo.media.video.VideoPlayTask;
import com.mikel.projectdemo.media.video.VideoViewPagerAdapter;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.ui.PlayerView;
import androidx.viewpager2.widget.ViewPager2;

public class MediaFragment extends Fragment {
    private ViewPager2 mViewPager2;
    private VideoViewPagerAdapter mVideoViewPagerAdapter;
    private boolean onFragmentResume;
    private boolean onFragmentVisible;
    public static MediaFragment build() {
        return new MediaFragment();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_media, null, true);
        initUI(rootView);
        return rootView;
    }
    private void initUI(View rootView) {
        mViewPager2 = rootView.findViewById(R.id.viewpager2);
        mVideoViewPagerAdapter = new VideoViewPagerAdapter(getActivity());
        mVideoViewPagerAdapter.setDataList(VideoPlayManager.buildTestVideoUrls());
        mViewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager2.setAdapter(mVideoViewPagerAdapter);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("Video_Play_TAG", " on page selected = " + position);
                View itemView = mViewPager2.findViewWithTag(position);
                PlayerView simpleExoPlayerView = itemView.findViewById(R.id.play_view);
                VideoPlayManager.getInstance(AppUtil.getApplicationContext()).setCurVideoPlayTask(new VideoPlayTask(simpleExoPlayerView,
                        mVideoViewPagerAdapter.getUrlByPos(position)));
                if(onFragmentResume && onFragmentVisible) {
                    VideoPlayManager.getInstance(AppUtil.getApplicationContext()).startPlay();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            onFragmentVisible = true;
            VideoPlayManager.getInstance(AppUtil.getApplicationContext()).resumePlay();
            Log.d("Video_Play_TAG", " video fragment可见");
        }else {
            onFragmentVisible = false;
            VideoPlayManager.getInstance(AppUtil.getApplicationContext()).pausePlay();
            Log.d("Video_Play_TAG", " video fragment不可见 ");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onFragmentResume = true;
        if(onFragmentVisible) {
            VideoPlayManager.getInstance(AppUtil.getApplicationContext()).resumePlay();
        }
        Log.d("Video_Play_TAG", " video fragment Resume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        onFragmentResume = false;
        VideoPlayManager.getInstance(AppUtil.getApplicationContext()).pausePlay();
        Log.d("Video_Play_TAG", " video fragment Pause ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VideoPlayManager.getInstance(getActivity()).stopPlay();
    }
}
