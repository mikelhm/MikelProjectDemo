package com.mikel.projectdemo.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class VideoTestActivity extends Activity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VideoTestActivity.class);
        context.startActivity(intent);
    }
}
