package com.mikel.projectdemo.jetpack.service.paging3;
import android.content.Context;
import android.util.Log;

import com.mikel.projectdemo.jetpack.service.db.dao.PoetryDao;
import com.mikel.projectdemo.jetpack.service.db.dao.RemoteKeyDao;
import com.mikel.projectdemo.jetpack.service.db.database.AppDatabase;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.model.ResultList;
import com.mikel.projectdemo.jetpack.service.model.RemoteKey;
import com.mikel.projectdemo.jetpack.service.network.PagingNetWorkApiService;
import com.mikel.projectdemo.jetpack.service.repository.RepositoryManager;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.experimental.UseExperimental;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * RemoteMediator 的主要作用是：在 Pager 耗尽数据或现有数据失效时，从网络加载更多数据。
 */
//RemoteMediator 目前是实验性的 API ，所有实现 RemoteMediator 的类都需要添加 @OptIn(ExperimentalPagingApi::class) 注解
@UseExperimental(markerClass = ExperimentalPagingApi.class)
public class PagingRemoteMediator extends RxRemoteMediator<Integer, Poetry> {
    private Context mContext;
    private AppDatabase mAppDb;
    private PoetryDao mPoetryDao;
    private RemoteKeyDao mRemoteKeyDao;
    private PagingNetWorkApiService mNetworkService;
    public PagingRemoteMediator(Context context, PagingNetWorkApiService pagingNetWorkApiService, AppDatabase db) {
        this.mContext = context;
        this.mAppDb = db;
        this.mPoetryDao = this.mAppDb .poetryDao();
        this.mRemoteKeyDao = this.mAppDb.remoteKeyDao();
        this.mNetworkService = pagingNetWorkApiService;
    }

    @NotNull
    @Override
    public Single<MediatorResult> loadSingle(@NotNull LoadType loadType, @NotNull PagingState<Integer, Poetry> pagingState) {
        int loadKey = Constants.PAGING_FIRST_PAGE;
        Log.d(Constants.TAG, " Paging3框架，RemoteMediator loadSingle call,  loadType = " + loadType);
        switch (loadType) {
            case REFRESH:
                break;
            case PREPEND:
                return Single.just(new MediatorResult.Success(true));
            case APPEND:
                /*** 从数据库中查询下次请求的page_key*/
                RemoteKey remoteKey = mRemoteKeyDao.getRemoteKey(Constants.PAGING_REMOTE_KEY_LABEL_PORTRY);
                if(remoteKey != null) {
                    loadKey = Integer.parseInt(remoteKey.getPage_key());
                }
                break;
        }
        final int fLoadKey = loadKey;
        Log.d(Constants.TAG, " Paging3框架，RemoteMediator loadSingle call,  loadkey = " + loadKey);
        /**
         *  todo
         *  由于网络接口失效，这里构建测试数据 模拟服务端的返回，
         *  正常情况下直接使用 下面被注释掉的代码mPagingNetWorkApiService.fetchPoetryListForPaging3
         */
//        return mNetworkService.fetchPoetryListForPaging3(String.valueOf(fLoadKey), String.valueOf(Constants.PAGING_PAGE_SIZE))
//                .subscribeOn(Schedulers.io())
//                .map((Function<ResultList<Poetry>, MediatorResult>) resultList -> {
//                    mAppDb.runInTransaction(() -> {
//                        if (loadType == LoadType.REFRESH) {
//                            mPoetryDao.deleteAll();
//                            Log.d(Constants.TAG, " refresh, delete the cache");
//                        }
//                        /**
//                         * 根据response更新下一个请求的页码 ：当前请求的remoteKey.getNext_page() 加1
//                         */
//                        RemoteKey insertRemoteKey = new RemoteKey(Constants.PAGING_REMOTE_KEY_LABEL_PORTRY, String.valueOf(fLoadKey + 1));
//                        mRemoteKeyDao.insertRemoteKey(insertRemoteKey);
//                        /**
//                         * 保存网络请求的数据到数据库
//                         */
//                        mPoetryDao.insertAll(resultList.getData());
//                    });
//
//                    return new MediatorResult.Success(resultList.getData() == null || resultList.getData().size() == 0);
//                })
//                .onErrorResumeNext(e -> {
//                    Log.e(Constants.TAG, " paging remote mediator load on error =  " + e.toString());
//                    if (e instanceof IOException || e instanceof HttpException) {
//                        return Single.just(new MediatorResult.Error(e));
//                    }
//
//                    return Single.error(e);
//                });
        /**
         * 以下是模拟服务端回包数据
         */
        ResultList<Poetry> testResultList = buildTestResultList(String.valueOf(loadKey));
        RemoteKey insertRemoteKey = new RemoteKey(Constants.PAGING_REMOTE_KEY_LABEL_PORTRY, String.valueOf(fLoadKey + 1));
        mRemoteKeyDao.insertRemoteKey(insertRemoteKey);
        mPoetryDao.insertAll(testResultList.getData());
        return Single.just(new MediatorResult.Success(false));
    }

    private ResultList<Poetry>  buildTestResultList(String page) {
        ResultList<Poetry> resultList = new ResultList<>();
        List<Poetry> poetries = new ArrayList<>();
        for(int i=0; i<3; i++){
            long time = System.currentTimeMillis() + i;
            Poetry poetry = new Poetry();
            poetry.poetry_id = (int)time;
            poetry.title = "NetworkResponse_第" + page + "页_第." + i
                    +"条数据_" + RepositoryManager.CACHE_TITLE[i] + "_" + time;
            poetry.content= RepositoryManager.CACHE_CONTENT[i];
            poetry.authors= RepositoryManager.CACHE_AUTHOR[i];
            poetries.add(poetry);
        }
        resultList.setCode(200);
        resultList.setMessage("Test Data Success");
        resultList.setResult(poetries);
        return resultList;
    }

}
