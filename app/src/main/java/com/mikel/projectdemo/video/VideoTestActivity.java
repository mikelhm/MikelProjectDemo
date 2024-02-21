package com.mikel.projectdemo.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.uiframework.VideoTestFragment;

public class VideoTestActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VideoTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        FragmentManager fragmentManager = getSupportFragmentManager();
        VideoTestFragment videoTestFragment = VideoTestFragment.build();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, videoTestFragment);
        fragmentTransaction.commit();

    }
}
