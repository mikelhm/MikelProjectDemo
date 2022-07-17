package com.mikel.projectdemo.jetpack.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.databinding.FragmentPoetryDetailBinding;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.utils.Utils;
import com.mikel.projectdemo.jetpack.viewmodel.PoetryViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

/**
 * Created by mikeluo on 2019/3/17.
 *  * 1. Fragment,Activity这些content应该要持有adapter, binding, ViewModel
 * 2. ViewModel里面应该要提供一个接口 setDataInfo：对viewModel里面的observerField成员变量进行赋值—同步数据到UI
 * 3. 对于2的 setDataInfo接口什么时候调用呢？
 *    场景1：在onCreate时加载本地缓存或者DB拿到本地数据。 场景2：网络拉取—回包拿到数据后需要一个观察者模式通知UI
 * 4. 对于场景2的观察者模式—该demo采用了liveData,liveData.setValue方法调用后，内部会触发Observer.onChanged方法
 *      接着在onChanted方法里面 调用viewModel.setDataInfo() —— dataBinding的作用会同步到UI
 */


public class PoetryFragment extends Fragment {
    public static final String TAG = "PoetryFragment";
    private static final String KEY_POETRY_ID = "poetry_id";
    //框架自动生成FragmentPoetryDetailBinding
    private FragmentPoetryDetailBinding mFragmentPoetryDetailBinding;
    private PoetryViewModel mPoetryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mFragmentPoetryDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_poetry_detail, container, false);
        return mFragmentPoetryDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PoetryViewModel.Factory factory = new PoetryViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_POETRY_ID));
        mPoetryViewModel = new PoetryViewModel(this.getActivity().getApplication(),
                getArguments().getString(KEY_POETRY_ID));
        mFragmentPoetryDetailBinding.setIsLoading(true);
       //先读本地
        mPoetryViewModel.loadDataInfo(getArguments().getString(KEY_POETRY_ID));
        observeViewModelLocal(mPoetryViewModel);

        //todo 由于网络接口失效， 先注释掉网络请求。
//        mPoetryViewModel.requestSearchPoetry(getArguments().getString(KEY_POETRY_ID));
//        observeViewModelNetwork(mPoetryViewModel);
    }

    /**
     * LiveData被观察者和观察者activty ,fragment建立订阅关系
     * @param viewModel
     */
    private void observeViewModelNetwork(final PoetryViewModel viewModel) {
        viewModel.getmPoetryObservableNetwork().observe(this, new Observer<Poetry>() {
            @Override
            public void onChanged(Poetry poetry) {
                Utils.printPoetryInfo(poetry);//打日志
                if (poetry != null) {
                    mFragmentPoetryDetailBinding.setIsLoading(false);// binding调用setXXX方法后，数据同步到UI
                    mFragmentPoetryDetailBinding.setPoetry(poetry);// binding调用setXXX方法后，数据同步到UI
                }
            }
        });
    }

    private void observeViewModelLocal(final PoetryViewModel viewModel) {
        viewModel.getmPoetryObservableLocal().observe(this, new Observer<Poetry>() {
            @Override
            public void onChanged(Poetry poetry) {
                Utils.printPoetryInfo(poetry);//打日志
                if (poetry != null) {
                    mFragmentPoetryDetailBinding.setIsLoading(false);// binding调用setXXX方法后，数据同步到UI
                    mFragmentPoetryDetailBinding.setPoetry(poetry);// binding调用setXXX方法后，数据同步到UI
                }
            }
        });
    }
    /**
     * 根据poetryId创建一个PoetryFragment
     * @param poetryID
     * @return
     */
    public static PoetryFragment forPoetry(String poetryID) {
        PoetryFragment fragment = new PoetryFragment();
        Bundle args = new Bundle();

        args.putString(KEY_POETRY_ID, poetryID);
        fragment.setArguments(args);

        return fragment;
    }
}
