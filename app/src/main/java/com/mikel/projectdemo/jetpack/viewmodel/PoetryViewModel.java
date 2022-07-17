package com.mikel.projectdemo.jetpack.viewmodel;

import android.app.Application;

import com.mikel.projectdemo.jetpack.service.db.database.AppDatabase;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.network.RetrofitManager;
import com.mikel.projectdemo.jetpack.service.utils.Utils;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * Created by mikeluo on 2019/3/17.
 */

public class PoetryViewModel extends AndroidViewModel {

    /**
     * 使用同一个obserable监听  只会响应最后的一个
     * 这里区分本地的和network的
     */
    // MutableLiveData继承liveData， 提供了setValue和postValue方法。liveData 是抽象类
    private MutableLiveData<Poetry> mPoetryObservableLocal= new MutableLiveData<>();
    private MutableLiveData<Poetry> mPoetryObservableNetwork = new MutableLiveData<>();
    private final String poetryID;

    public PoetryViewModel(Application application, String poetryID) {
        super(application);
        this.poetryID = poetryID;
    }

    /**
     * 读DB或者缓存
     * 根据具体的名字获取Poetry
     */
    public Poetry loadDataInfo(String name) {
        Utils.printMsg(" poetry detail load info");
        Poetry poetry = AppDatabase.getInstance(this.getApplication()).poetryDao().getPoetryByName(name);
        mPoetryObservableLocal.setValue(poetry);// setValue 需要在主进程调用
        return poetry;
    }

    /**
     *  发送网络请求
     *  真实的请求逻辑封装到了RetrofitManager
     * 根据名称搜索某个poetry
     * @param name
     */
    public void requestSearchPoetry(String name) {
        mPoetryObservableNetwork = RetrofitManager.getInstance(this.getApplication()).searchPoetry(name);
    }

    /**
     * 返回LiveData 对象
     * @return
     */
    public LiveData<Poetry> getmPoetryObservableNetwork() {
        return mPoetryObservableNetwork;
    }

    public LiveData<Poetry> getmPoetryObservableLocal() {
        return mPoetryObservableLocal;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;

        private final String poetryID;

        public Factory( Application application, String poetryID) {
            this.application = application;
            this.poetryID = poetryID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PoetryViewModel(application, poetryID);
        }
    }
}
