package com.mikel.projectdemo.jetpack.view.ui;

import android.os.Bundle;

import com.mikel.projectdemo.R;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by mikeluo on 2019/3/19.
 */

public class PoetryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_poetry);

        if (savedInstanceState == null) {
            PoetryFragment poetryFragment = PoetryFragment.forPoetry(getIntent().getStringExtra("title"));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, poetryFragment, PoetryListFragmentForPaging2Local.TAG).commit();
        }
    }
}
