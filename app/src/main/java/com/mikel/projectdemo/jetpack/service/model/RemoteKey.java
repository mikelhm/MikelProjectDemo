package com.mikel.projectdemo.jetpack.service.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "remote_key_table")
public class RemoteKey {
    @NonNull
    @ColumnInfo
    @PrimaryKey(autoGenerate = true) //主键
    private int key_label;//页码标识，代表具体哪个分页

    @ColumnInfo
    private String page_key;//下一次请求的分页页码

    public RemoteKey() {
    }

    public RemoteKey(int label, String page) {
        this.key_label = label;
        this.page_key = page;
    }

    @Override
    public String toString() {
        return "RemoteKey:" +
                "label =" + key_label +
                "page =" + page_key;
    }

    @NonNull
    public int getKey_label() {
        return key_label;
    }

    public void setKey_label(@NonNull int key_label) {
        this.key_label = key_label;
    }


    public String getPage_key() {
        return page_key;
    }

    public void setPage_key( String next_page) {
        this.page_key = next_page;
    }
}
