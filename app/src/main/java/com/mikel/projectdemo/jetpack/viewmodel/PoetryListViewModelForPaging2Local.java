package com.mikel.projectdemo.jetpack.viewmodel;

import android.app.Application;

import com.mikel.projectdemo.jetpack.service.db.dao.PoetryDao;
import com.mikel.projectdemo.jetpack.service.db.database.AppDatabase;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.paging2.LocalPageKeyedDataSource;
import com.mikel.projectdemo.jetpack.service.paging2.LocalPageKeyedDataSourceFactory;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by mikeluo on 2019/3/17.
 */

public class PoetryListViewModelForPaging2Local extends AndroidViewModel {
    PoetryDao mPortryDao;
    private LiveData<PagedList<Poetry>> localLiveDataPageListPoetry;
    private LocalPageKeyedDataSourceFactory mLocalPageKeyedDataSourceFactory;
    public PoetryListViewModelForPaging2Local(Application application) {
        super(application);

        mPortryDao =  AppDatabase.getInstance(this.getApplication()).poetryDao();
        //本地数据源
        mLocalPageKeyedDataSourceFactory = new LocalPageKeyedDataSourceFactory(mPortryDao);
        localLiveDataPageListPoetry =  new LivePagedListBuilder<>(mLocalPageKeyedDataSourceFactory,
                LocalPageKeyedDataSource.PAGE_SIZE).build();
    }

    public LiveData<PagedList<Poetry>> getLocalLiveDataPageListPoetry() {
        return localLiveDataPageListPoetry;
    }
}
