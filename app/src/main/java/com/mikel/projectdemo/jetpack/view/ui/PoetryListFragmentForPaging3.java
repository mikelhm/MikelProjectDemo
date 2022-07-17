package com.mikel.projectdemo.jetpack.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.databinding.FragmentPoetryListBinding;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.utils.Constants;
import com.mikel.projectdemo.jetpack.view.adapter.PoetryListAdapterForPaging3;
import com.mikel.projectdemo.jetpack.view.callback.PoetryClickCallBack;
import com.mikel.projectdemo.jetpack.viewmodel.PoetryListViewModelForPaging3;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;


public class PoetryListFragmentForPaging3 extends Fragment {

    public static final String TAG = "PoetryListFragmentForPaging3";
    //分页PagedListAdapter
    private PoetryListAdapterForPaging3 mCustomPagedPoetryAdapter;
    private FragmentPoetryListBinding mFragmentPoetryListBinding;
    private PoetryListViewModelForPaging3 mPoetryListViewModelForPaging3;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentPoetryListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_poetry_list, container, false);
        mCustomPagedPoetryAdapter = new PoetryListAdapterForPaging3(mPoetryClickCallBack);
        //mFragmentPoetryListBinding.poetryList根据layout xml的id
        mFragmentPoetryListBinding.poetryList.setAdapter(mCustomPagedPoetryAdapter);
        mFragmentPoetryListBinding.setIsLoading(true);
        return mFragmentPoetryListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //1. 初始化viewModel
        mPoetryListViewModelForPaging3 = new PoetryListViewModelForPaging3(getContext());
        //2. viewmodel 获取 obserable ,注册数据监听
        subscribePagingData();
    }

    private void subscribePagingData() {
        mPoetryListViewModelForPaging3.getPoetryObservable().subscribe(poetryPagingData -> {
            Log.d(Constants.TAG, " paging3 observe data changed");
            mFragmentPoetryListBinding.setIsLoading(false);//去掉loading
            mCustomPagedPoetryAdapter.submitData(getLifecycle(), poetryPagingData);
        });
    }


    private PoetryClickCallBack mPoetryClickCallBack = new PoetryClickCallBack() {
        @Override
        public void onClick(Poetry poetry) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((Paging3MainActivity) getActivity()).show(poetry);
            }
        }
    };
}
