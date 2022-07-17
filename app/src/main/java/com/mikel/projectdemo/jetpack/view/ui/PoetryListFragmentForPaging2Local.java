package com.mikel.projectdemo.jetpack.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.databinding.FragmentPoetryListBinding;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.repository.RepositoryManager;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import com.mikel.projectdemo.jetpack.service.utils.Utils;
import com.mikel.projectdemo.jetpack.view.adapter.PoetryListAdapterForPaging2;
import com.mikel.projectdemo.jetpack.view.callback.PoetryClickCallBack;
import com.mikel.projectdemo.jetpack.viewmodel.PoetryListViewModelForPaging2Local;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

/**
 * Created by mikeluo on 2019/3/17.
 */

public class PoetryListFragmentForPaging2Local extends Fragment {

    public static final String TAG = "PoetryListFragmentForPaging2";
    //分页PagedListAdapter
    private PoetryListAdapterForPaging2 mCustomPagedPoetryAdapter;
    private FragmentPoetryListBinding mFragmentPoetryListBinding;
    private PoetryListViewModelForPaging2Local mPoetryListViewModelForPaging2Local;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentPoetryListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_poetry_list, container, false);
        mCustomPagedPoetryAdapter = new PoetryListAdapterForPaging2(mPoetryClickCallBack);
        //mFragmentPoetryListBinding.poetryList根据layout xml的id
        mFragmentPoetryListBinding.poetryList.setAdapter(mCustomPagedPoetryAdapter);
        mFragmentPoetryListBinding.setIsLoading(true);
        return mFragmentPoetryListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //构建本地数据
        RepositoryManager.getInstance().buildLocalTestData(getContext());

        //1. 初始化viewModel
        mPoetryListViewModelForPaging2Local = new PoetryListViewModelForPaging2Local(this.getActivity().getApplication());
        //2.注册本地监听
        registerViewModelLocal();
    }

    /**
     * 读取DB 或者 缓存观察者回调监听
     */
    private void registerViewModelLocal() {
        Log.d(Constants.TAG, "注册本地监听");
        //分页模式
        mPoetryListViewModelForPaging2Local.getLocalLiveDataPageListPoetry().observe(this, new Observer<PagedList<Poetry>>() {
            @Override
            public void onChanged(final PagedList<Poetry> poetries) {
                Utils.printMsg(" -----------------local paging onChanged----------------");
                Utils.printPoetryInfo( poetries);
                mFragmentPoetryListBinding.setIsLoading(false);//去掉loading
                mCustomPagedPoetryAdapter.submitList(poetries);
            }
        });
    }

    private PoetryClickCallBack mPoetryClickCallBack = new PoetryClickCallBack() {
        @Override
        public void onClick(Poetry poetry) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((Paging2MainActivity) getActivity()).show(poetry);
            }
        }
    };
}
