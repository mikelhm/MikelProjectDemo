package com.mikel.projectdemo.jetpack.service.utils;

import android.util.Log;

import com.mikel.projectdemo.jetpack.service.model.Poetry;

import java.util.List;


/**
 * Created by mikeluo on 2019/3/19.
 */

public class Utils {

    public static void printMsg(String msg) {
        Log.i(Constants.TAG, msg);
    }
    public static void printPoetryInfo(List<Poetry> poetries) {
        if (poetries == null || poetries.size() == 0) {
            Log.i(Constants.TAG, " poetry list is empty");
            return;
        }

        for (int i = 0; i<poetries.size(); i++) {
            Poetry poetry = poetries.get(i);
            if (poetry == null) {
                Log.i(Constants.TAG, " 第 " + i + " 个poetry  is null");
                return;
            }
            Log.i(Constants.TAG, " 第 " + i + " 个poetry title =" + poetry.title + " \n");
        }
    }

    public static void printPoetryInfo(Poetry poetry) {
        if (poetry == null) {
            Log.i(Constants.TAG, " poetry  is null");
            return;
        }
        Log.i(Constants.TAG, " poetry title =" + poetry.title);
    }
}
