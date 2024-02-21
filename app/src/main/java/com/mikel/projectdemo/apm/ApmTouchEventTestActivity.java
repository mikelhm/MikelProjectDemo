package com.mikel.projectdemo.apm;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.mikel.projectdemo.R;

public class ApmTouchEventTestActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_touch_event_test);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    try {
      Thread.sleep(8000);
    } catch (Exception e) {

    }
    return super.dispatchTouchEvent(ev);
  }
}
