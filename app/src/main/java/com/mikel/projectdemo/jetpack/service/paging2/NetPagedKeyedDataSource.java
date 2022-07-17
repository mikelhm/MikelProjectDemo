package com.mikel.projectdemo.jetpack.service.paging2;

import android.content.Context;
import android.util.Log;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.network.RetrofitManager;
import com.mikel.projectdemo.jetpack.service.repository.RepositoryManager;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import com.mikel.projectdemo.jetpack.service.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

public class NetPagedKeyedDataSource extends PageKeyedDataSource<Integer, Poetry> {
    public static final int PAGE_SIZE = 3;
    private int CUR_PAGE = 0;
    private Context mContext;
    public NetPagedKeyedDataSource(Context context) {
        this.mContext = context;
    }
    @Override
    public void loadInitial(@NonNull @NotNull LoadInitialParams<Integer> params, @NonNull @NotNull LoadInitialCallback<Integer, Poetry> callback) {
        Log.w(Constants.TAG, "network  load intial ");
        CUR_PAGE = 0;

        /**
         *  todo
         *  由于网络接口失效，这里构建测试数据 模拟服务端的返回，
         *  正常情况下直接使用下面被注释掉的代码 RetrofitManager.getInstance(mContext).fetchPoetryList
         */
//        RetrofitManager.getInstance(mContext).fetchPoetryList(String.valueOf(0), String.valueOf(PAGE_SIZE), new RetrofitManager.ReqCallBack() {
//            @Override
//            public void updateData(List<Poetry> dataList) {
//                Utils.printPoetryInfo(dataList);
//                callback.onResult(dataList, null, 1);
//            }
//        });
        buildNetTestData(String.valueOf(0), new RetrofitManager.ReqCallBack() {
            @Override
            public void updateData(List<Poetry> dataList) {
                Utils.printPoetryInfo(dataList);
                callback.onResult(dataList, null, 1);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, Poetry> callback) {

    }

    @Override
    public void loadAfter(@NonNull @NotNull LoadParams<Integer> params, @NonNull @NotNull LoadCallback<Integer, Poetry> callback) {
        CUR_PAGE = params.key;
        int startID = params.key * params.requestedLoadSize;
        Log.w(Constants.TAG, "network  load after  start id = " + startID + " size= " + params.requestedLoadSize);
        /**
         *  todo
         *  由于网络接口失效，这里构建测试数据 模拟服务端的返回，
         *  正常情况下直接使用下面被注释掉的代码 RetrofitManager.getInstance(mContext).fetchPoetryList
         */
//        RetrofitManager.getInstance(mContext).fetchPoetryList(String.valueOf(params.key),
//                String.valueOf(params.requestedLoadSize), new RetrofitManager.ReqCallBack() {
//                    @Override
//                    public void updateData( List<Poetry> dataList) {
//                        Utils.printPoetryInfo(dataList);
//                        if (dataList != null) {
//                            callback.onResult(dataList, params.key + 1);
//                        }
//                    }
//                });

        buildNetTestData(String.valueOf(params.key), new RetrofitManager.ReqCallBack() {
            @Override
            public void updateData(List<Poetry> dataList) {
                Utils.printPoetryInfo(dataList);
                if (dataList != null) {
                    callback.onResult(dataList, params.key + 1);
                }
            }
        });
    }

    private void buildNetTestData(String page, RetrofitManager.ReqCallBack callback) {
        List<Poetry> poetries = new ArrayList<>();
        for(int i=0; i<3; i++){
            long time = System.currentTimeMillis() + i;
            Poetry poetry = new Poetry();
            poetry.poetry_id = (int)time;
            poetry.title = "NetworkResponse_页" + page + "_no." + i
                    +"_" + RepositoryManager.CACHE_TITLE[i] + "_" + time;
            poetry.content= RepositoryManager.CACHE_CONTENT[i];
            poetry.authors= RepositoryManager.CACHE_AUTHOR[i];
            poetries.add(poetry);
        }
        //回调数据
        callback.updateData(poetries);
    }
}
