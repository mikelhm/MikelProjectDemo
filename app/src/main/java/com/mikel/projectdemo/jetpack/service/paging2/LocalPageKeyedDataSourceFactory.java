package com.mikel.projectdemo.jetpack.service.paging2;

import com.mikel.projectdemo.jetpack.service.db.dao.PoetryDao;
import com.mikel.projectdemo.jetpack.service.model.Poetry;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class LocalPageKeyedDataSourceFactory extends DataSource.Factory<Integer, Poetry> {
    private PoetryDao mPoetryDao;
    private DataSource<Integer, Poetry> mDataSource;
    public LocalPageKeyedDataSourceFactory(PoetryDao poetryDao) {
        mPoetryDao = poetryDao;
    }
    @NonNull
    @NotNull
    @Override
    public DataSource<Integer, Poetry> create() {
        mDataSource = new LocalPageKeyedDataSource(mPoetryDao);
        return mDataSource;
    }

    public DataSource<Integer, Poetry> getDataSource() {
        return mDataSource;
    }
}
