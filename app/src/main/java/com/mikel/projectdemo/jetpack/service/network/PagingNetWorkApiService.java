package com.mikel.projectdemo.jetpack.service.network;
//lib添加retrofit2.jar包
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.model.ResultList;

import androidx.paging.RemoteMediator;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by mikeluo on 2019/3/16.
 */

public interface PagingNetWorkApiService {
    public static String HTTPS_API_OPEN_URL = "https://api.apiopen.top/";

    //获取唐诗列表Rxjava
    @GET("getTangPoetry")
    Observable<ResultList<Poetry>> fetchPoetryListForRxJava(@Query("page") String page, @Query("count") String count);


    @GET("getTangPoetry")
    Single<ResultList<Poetry>> fetchPoetryListForPaging3(@Query("page") String page, @Query("count") String count);

    //根据名称搜索唐诗
    @GET("searchPoetry")
    Observable<ResultList<Poetry>> searchPoetry(@Query("name") String name);


}
