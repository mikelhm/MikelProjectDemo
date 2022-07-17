package com.mikel.projectdemo.jetpack.service.db.database;
import android.content.Context;

import com.mikel.projectdemo.jetpack.service.db.dao.PoetryDao;
import com.mikel.projectdemo.jetpack.service.db.dao.RemoteKeyDao;
import com.mikel.projectdemo.jetpack.service.model.Poetry;
import com.mikel.projectdemo.jetpack.service.model.RemoteKey;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by mikeluo on 2019/3/18.
 */
@Database(entities = {Poetry.class, RemoteKey.class}, version = 1, exportSchema = false) // 声明版本号1
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract PoetryDao poetryDao();
    public abstract RemoteKeyDao remoteKeyDao();

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null) {//单例设计模式 双重校验
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,
                            "mikel_room.db").
                            addMigrations(AppDatabase.MIGRATION_1_2).// 修改数据库字段时更新数据库
                            allowMainThreadQueries().//允许在主线程读取数据库
                            build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 数据库变动添加Migration
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // todo 如果有修改数据库的字段 可以使用database.execSQL() 同时 修改数据库的version
        }
    };
}
