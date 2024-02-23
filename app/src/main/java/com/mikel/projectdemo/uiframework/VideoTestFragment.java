package com.mikel.projectdemo.uiframework;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.mikel.projectdemo.R;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;


public class VideoTestFragment extends Fragment {
    public static VideoTestFragment build() {
        return new VideoTestFragment();
    }

    private Context mContext;
    private SimpleExoPlayer mSimpleExoPlayer;
    private PlayerView playerView;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_item, null, true);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {
        mSimpleExoPlayer = new SimpleExoPlayer.Builder(getActivity()).build();
        // 准备要播放的媒体资源
        MediaItem mediaItem = MediaItem.fromUri("https://vfx.mtime.cn/Video/2019/01/15/mp4/190115161611510728_480.mp4");
        mSimpleExoPlayer.setMediaItem(mediaItem);
        // 将ExoPlayer关联到要显示视频的View
        playerView = rootView.findViewById(R.id.player_view);
        playerView.setPlayer(mSimpleExoPlayer);
    }

    public void startPlay() {
        // 准备播放器
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        pausePlay();
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    public void resumePlay() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(true);
        } else {
            startPlay();
        }
    }

    public void pausePlay() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumePlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        pausePlay();
    }
}
