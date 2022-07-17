package com.mikel.projectdemo.jetpack.viewmodel;

import android.app.Application;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.paging2.LocalPageKeyedDataSource;
import com.mikel.projectdemo.jetpack.service.paging2.NetPagedKeyedDataSourceFactory;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by mikeluo on 2019/3/17.
 */

public class PoetryListViewModelForPaging2Network extends AndroidViewModel {
    private LiveData<PagedList<Poetry>> netLiveDataPageListPoetry;
    private NetPagedKeyedDataSourceFactory mNetPagedKeyedDataSourceFactory;
    public PoetryListViewModelForPaging2Network(Application application) {
        super(application);
        //网络数据源
        mNetPagedKeyedDataSourceFactory = new NetPagedKeyedDataSourceFactory(getApplication());
        netLiveDataPageListPoetry =  new LivePagedListBuilder<>(mNetPagedKeyedDataSourceFactory,
                LocalPageKeyedDataSource.PAGE_SIZE).build();
    }
    public LiveData<PagedList<Poetry>> getNetLiveDataPageListPoetry() {
        return netLiveDataPageListPoetry;
    }
}
