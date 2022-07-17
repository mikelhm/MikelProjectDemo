package com.mikel.projectdemo.jetpack.view.adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Created by mikeluo on 2019/3/17.
 */

public class CustomBindingAdapter {//xml isLoading
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
