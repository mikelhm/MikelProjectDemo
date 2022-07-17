package com.mikel.projectdemo.jetpack.service.model;

/**
 * Created by mikeluo on 2019/3/16.
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 实体类—唐诗
 */
@Entity(tableName = "poetry_table") //数据库表名poetry_table
public class Poetry {
    @NonNull
    @ColumnInfo
    @PrimaryKey(autoGenerate = true) //主键
    public int poetry_id;
    @NonNull
    @ColumnInfo
    public String title;   //标题
    @NonNull
    @ColumnInfo //列
    public String content; //内容
    @NonNull
    @ColumnInfo //列
    public String authors;  // 作者

    public Poetry() {
    }

    @Override
    public String toString() {
        return "Poetry:" +
                "id=" + poetry_id +
                "title =" + title +
                ", content =" + content +
                ", author =" + authors;
    }

    public int getPoetry_id() {
        return poetry_id;
    }

    public void setPoetry_id(int poetry_id) {
        this.poetry_id = poetry_id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(@NonNull String authors) {
        this.authors = authors;
    }
}
