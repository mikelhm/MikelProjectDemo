package com.mikel.projectdemo.jetpack.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mikel.projectdemo.R;
import com.mikel.projectdemo.databinding.PoetryListItemBinding;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.view.callback.PoetryClickCallBack;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 数据源pageAdapter
 * 继承框架的PagedListAdapter，自定义的Adapter 无需再持有List<Poetry>
 */
public class PoetryListAdapterForPaging2 extends PagedListAdapter<Poetry, PoetryListAdapterForPaging2.PoetryViewHolder> {

    private final PoetryClickCallBack mPoetryClickCallback;

    public PoetryListAdapterForPaging2(PoetryClickCallBack poetryClickCallBack) {
        super(new DiffUtil.ItemCallback<Poetry>() {

            @Override
            public boolean areItemsTheSame(@NonNull Poetry oldItem, @NonNull Poetry newItem) {
                return oldItem.title.equals(newItem.title);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Poetry oldItem, @NonNull Poetry newItem) {
                return oldItem.title.equals(newItem.title)
                        && oldItem.content.equals(newItem.content)
                        && oldItem.authors.equals(newItem.authors);
            }
        });
        this.mPoetryClickCallback = poetryClickCallBack;
    }

    @Override
    public PoetryListAdapterForPaging2.PoetryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //databind框架自动生成PoetryListItemBinding
        PoetryListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.poetry_list_item,
                        parent, false);

        binding.setCallback(mPoetryClickCallback);// 双向绑定；自动生成一个setXXX方法
        return new PoetryListAdapterForPaging2.PoetryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PoetryListAdapterForPaging2.PoetryViewHolder holder, int position) {
        Poetry poetry = getItem(position);
        holder.binding.setPoetry(poetry);
        holder.binding.executePendingBindings();
    }

    /**
     * holder类
     */
    static class PoetryViewHolder extends RecyclerView.ViewHolder {

        final PoetryListItemBinding binding;

        public PoetryViewHolder(PoetryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
