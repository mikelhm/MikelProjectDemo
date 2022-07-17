package com.mikel.projectdemo.jetpack.service.db.dao;

import com.mikel.projectdemo.jetpack.service.model.Poetry;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


/**
 * Created by mikeluo on 2019/3/18.
 * 封装增删改查的数据库接口
 */

@Dao  // DAO接口
public interface PoetryDao {
    /**
     * 查询所有
     * @return
     */
    @Query("SELECT * FROM poetry_table")
    List<Poetry> getAllPoetries();

    /**
     * 分页查询数据库
     * @param startId
     * @param size
     * @return
     */
    @Query("SELECT  * FROM poetry_table LIMIT :startId, :size")
    List<Poetry> getSpecificPoetries(int startId, int size);

    @Query("SELECT * FROM poetry_table WHERE title = :name")
    Poetry getPoetryByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Poetry> poetries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Poetry poetry);

    @Query("DELETE FROM poetry_table")
    void deleteAll();
}
