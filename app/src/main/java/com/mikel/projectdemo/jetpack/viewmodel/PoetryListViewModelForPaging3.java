package com.mikel.projectdemo.jetpack.viewmodel;
import android.content.Context;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.network.RetrofitManager;
import com.mikel.projectdemo.jetpack.service.paging3.PoetryListPagingSource;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;
import io.reactivex.Observable;
import kotlinx.coroutines.CoroutineScope;

/**
 * paging3 viewmodel
 */
public class PoetryListViewModelForPaging3 extends ViewModel {
    Pager<Integer, Poetry> mPager;
    PoetryListPagingSource mPoetryListPagingSource;

    //rxjava observable
    Observable<PagingData<Poetry>> mPoetryObservable;
    public PoetryListViewModelForPaging3(Context context) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        /**
         * 数据源
         */
        mPoetryListPagingSource = new PoetryListPagingSource(RetrofitManager.getInstance(context).getPagingNetWorkApiService());
        /**
         * Pager ：分页大管家, 使用网络数据源构造
         */
        mPager = new Pager<Integer, Poetry>(new PagingConfig(Constants.PAGING_PAGE_SIZE), () -> mPoetryListPagingSource);

        /**
         *  PagingRx.getObservable
         */
        mPoetryObservable = PagingRx.getObservable(mPager);
        PagingRx.cachedIn(mPoetryObservable, viewModelScope);
    }

    public Observable<PagingData<Poetry>> getPoetryObservable() {
        return mPoetryObservable;
    }
}
