package com.mikel.projectdemo.jetpack.service.paging2;
import android.content.Context;
import com.mikel.projectdemo.jetpack.service.model.Poetry;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class NetPagedKeyedDataSourceFactory extends DataSource.Factory<Integer, Poetry> {
    private Context mContext;

    NetPagedKeyedDataSource mNetPagedKeyedDataSource;
    public NetPagedKeyedDataSourceFactory(Context context) {
        this.mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public DataSource<Integer, Poetry> create() {
        mNetPagedKeyedDataSource =  new NetPagedKeyedDataSource(mContext);
        return mNetPagedKeyedDataSource;
    }

}
