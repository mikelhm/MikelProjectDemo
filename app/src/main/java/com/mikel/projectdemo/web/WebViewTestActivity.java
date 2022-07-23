package com.mikel.projectdemo.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class WebViewTestActivity extends Activity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WebViewTestActivity.class);
        context.startActivity(intent);
    }
}
