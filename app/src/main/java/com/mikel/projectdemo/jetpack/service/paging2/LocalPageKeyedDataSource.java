package com.mikel.projectdemo.jetpack.service.paging2;

import android.util.Log;

import com.mikel.projectdemo.jetpack.service.db.dao.PoetryDao;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import com.mikel.projectdemo.jetpack.service.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * 适用于根据页请求数据的场景
 */
public class LocalPageKeyedDataSource extends PageKeyedDataSource<Integer, Poetry> {
    public static final int PAGE_SIZE = 3;
    private PoetryDao mPoetryDao;
    public LocalPageKeyedDataSource(PoetryDao poetryDao) {
        mPoetryDao = poetryDao;
    }

    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params, @NonNull @NotNull LoadInitialCallback<Integer, Poetry> callback) {
        /**
         * 初始化只从数据库查首页 pageKey = 0, 下一页pageKey = 1
         */
        List<Poetry> firstPageList = mPoetryDao.getSpecificPoetries(0, PAGE_SIZE);
        Log.w(Constants.TAG, " load initial ");
        Utils.printPoetryInfo(firstPageList);
        callback.onResult(firstPageList, null, 1);
    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, Poetry> callback) {
        Log.w(Constants.TAG, " load before param key =  " + params.key);
    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, Poetry> callback) {
        int startID = params.key * params.requestedLoadSize;
        Log.w(Constants.TAG, " load after  start id = " + startID + " size= " + params.requestedLoadSize);
        /**
         * 每次只从数据库里 查一页数据
         */
        List<Poetry> dataList = mPoetryDao.getSpecificPoetries(startID, params.requestedLoadSize);
        Utils.printPoetryInfo(dataList);
        if (dataList != null) {
            callback.onResult(dataList, params.key + 1);
        }
    }
}
