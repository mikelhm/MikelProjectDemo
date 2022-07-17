package com.mikel.projectdemo.jetpack.service.paging3;

import android.util.Log;

import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.model.ResultList;
import com.mikel.projectdemo.jetpack.service.network.PagingNetWorkApiService;
import com.mikel.projectdemo.jetpack.service.repository.RepositoryManager;
import com.mikel.projectdemo.jetpack.service.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import io.reactivex.Single;
public class PoetryListPagingSource extends RxPagingSource<Integer, Poetry> {
    @NonNull
    private PagingNetWorkApiService mPagingNetWorkApiService;
    private int nextPageKey = 0;
    public PoetryListPagingSource(@NonNull PagingNetWorkApiService pagingNetWorkApiService) {
        mPagingNetWorkApiService = pagingNetWorkApiService;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, Poetry>> loadSingle(
            @NotNull LoadParams<Integer> params) {
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 0;
        }

        nextPageKey = nextPageNumber;

        Log.d(Constants.TAG, "Paging3框架，PagingSource loadSingle call,  page = " + nextPageKey);

        /**
         *  todo
         *  由于网络接口失效，这里构建测试数据 模拟服务端的返回，
         *  正常情况下直接使用下面被注释掉的代码mPagingNetWorkApiService.fetchPoetryListForPaging3
         */
//        return mPagingNetWorkApiService.fetchPoetryListForPaging3(String.valueOf(nextPageKey),
//                String.valueOf(Constants.PAGING_PAGE_SIZE))
//                .subscribeOn(Schedulers.io())
//                .map(this::toLoadResult)
//                .onErrorReturn(LoadResult.Error::new);
        ResultList<Poetry> testResultList = buildTestResultList(String.valueOf(nextPageKey));
        return Single.just(toLoadResult(testResultList));
    }

    private LoadResult<Integer, Poetry> toLoadResult(
            @NonNull ResultList<Poetry> resultList) {
        return new LoadResult.Page<>(
                resultList.getData(),
                null, // Only paging forward.
                nextPageKey + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, Poetry> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Poetry> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }

    /**
     * 模拟服务端返回，构造分页测试数据
     * @param page
     * @return
     */
    private ResultList<Poetry>  buildTestResultList(String page) {
        ResultList<Poetry> resultList = new ResultList<>();
        List<Poetry> poetries = new ArrayList<>();
        for(int i=0; i<3; i++){
            long time = System.currentTimeMillis() + i;
            Poetry poetry = new Poetry();
            poetry.poetry_id = (int)time;
            poetry.title = "NetworkResponse_第" + page + "页_第" + i
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
