package com.mikel.projectdemo.jetpack.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.jetpack.service.model.Poetry;

import androidx.appcompat.app.AppCompatActivity;

public class Paging3MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging3_main);

        if (savedInstanceState == null) {
            PoetryListFragmentForPaging3 fragment = new PoetryListFragmentForPaging3();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, PoetryListFragmentForPaging3.TAG).commit();
        }
    }


    /**
     * 点击具体某个item跳转
     * @param poetry
     */
    public void show(Poetry poetry) {
        Intent intent = new Intent(this, PoetryDetailActivity.class);
        intent.putExtra("title", poetry.title);
        startActivity(intent);
    }
}

