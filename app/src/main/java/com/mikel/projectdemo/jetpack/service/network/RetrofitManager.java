package com.mikel.projectdemo.jetpack.service.network;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;
import android.util.Log;

import com.mikel.projectdemo.jetpack.service.db.database.AppDatabase;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.model.ResultList;
import com.mikel.projectdemo.jetpack.service.repository.RepositoryManager;
import com.mikel.projectdemo.jetpack.service.utils.Constants;

import org.jetbrains.annotations.NotNull;

/**
 * Created by mikeluo on 2019/3/17.
 */

public class RetrofitManager {
    private PagingNetWorkApiService pagingNetWorkApiService;
    private static RetrofitManager mRetrofitManager;
    private Context mContext;//manager需要持有context

    /**
     * 构造方法
     * 初始化GetTangDynastyService
     */
    private RetrofitManager(Context context) {
        this.mContext = context;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)//设置超时时间
                .retryOnConnectionFailure(true);
        client.addInterceptor(new CustomLoggingInterceptor());//添加打印日志的拦截器

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PagingNetWorkApiService.HTTPS_API_OPEN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(client.build())
                .build();

        pagingNetWorkApiService = retrofit.create(PagingNetWorkApiService.class);
    }

    /**
     * 单例设计模式
     * 双重检验锁
     * @return
     */
    public static RetrofitManager getInstance(Context context) {
        if (mRetrofitManager == null) {//双重校验锁
            synchronized(RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager(context);
                }
            }
        }
        return mRetrofitManager;
    }

    /**
     * 获取lis唐诗list的接口
     * 返回liveData
     * liveData.setValue会触发 Observer.onChanged   观察者模式
     */
    public void fetchPoetryList(String page, String count, ReqCallBack callback) {
        Log.d(Constants.TAG, "网络请求第" + page +"页");
        Observable<ResultList<Poetry>> resultListObservable = pagingNetWorkApiService.fetchPoetryListForRxJava(page, count);
        resultListObservable.subscribeOn(Schedulers.io()) // 在子线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回响应
                .subscribe(new Observer<ResultList<Poetry>>() { // 订阅
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull ResultList<Poetry> poetryResultList) {
                        Log.i(Constants.TAG, "onResponse() ");;
                        List<Poetry> poetries = poetryResultList.getData();
                        // 网络回包后，持久化到本地
                        AppDatabase.getInstance(mContext).poetryDao().insertAll(poetries);
                        //回调数据
                        callback.updateData(poetries);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(Constants.TAG, "onError()");
                        callback.updateData(null);
                        Log.i(Constants.TAG, "onFailure()");
                    }
                });
    }

    /**
     * 搜索具体的唐诗
     * @return
     */
    public MutableLiveData<Poetry> searchPoetry(String name) {
        final MutableLiveData<Poetry> data = new MutableLiveData<>();

        Observable<ResultList<Poetry>> resultListObservable =  pagingNetWorkApiService.searchPoetry(name);
        resultListObservable.subscribeOn(Schedulers.io()) // 在子线程中进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回响应
                .subscribe(new Observer<ResultList<Poetry>>() { // 订阅
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull ResultList<Poetry> poetryResultList) {
                        List<Poetry> poetries = poetryResultList.getData();
                        data.setValue(poetries.get(0));
                        Log.i(Constants.TAG, "onResponse()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                        Log.i(Constants.TAG, "onFailure()");
                    }
                });
        return data;
    }

    public interface ReqCallBack{
        void updateData( List<Poetry> dataList);
    }

    public PagingNetWorkApiService getPagingNetWorkApiService() {
        return pagingNetWorkApiService;
    }
}
