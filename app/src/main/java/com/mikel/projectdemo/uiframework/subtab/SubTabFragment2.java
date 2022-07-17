package com.mikel.projectdemo.uiframework.subtab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mikel.projectdemo.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubTabFragment2 extends Fragment {
    public static SubTabFragment2 build() {
        return new SubTabFragment2();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sub_tab_content2, null, true);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {
        Button webTestBtn = rootView.findViewById(R.id.test_webview_btn);
        FrameLayout webFrameLayout =  rootView.findViewById(R.id.webview_layout);
        WebView webView = new WebView(webFrameLayout.getContext());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLoadsImagesAutomatically(true);
        webView.loadUrl("https://www.baidu.com");
        webTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态添加WebView
                webFrameLayout.removeAllViews();
                webFrameLayout.addView(webView);
                final AnimatorSet animatorSet = new AnimatorSet();
                //缩放动画+淡入淡出动画
                animatorSet.playTogether(ObjectAnimator.ofFloat(webView, "scaleX", 0, 1)
                        .setDuration(1000),ObjectAnimator.ofFloat(webView, "scaleY", 0, 1)
                        .setDuration(1000), ObjectAnimator.ofFloat(webView, "alpha", 0, 1)
                        .setDuration(1000));
                //动画开始的原点
                webView.setPivotX(webFrameLayout.getWidth() - 20);
                webView.setPivotY(30);
                animatorSet.start();
            }
        });
    }
}
