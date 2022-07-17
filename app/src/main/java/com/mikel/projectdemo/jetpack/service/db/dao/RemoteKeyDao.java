package com.mikel.projectdemo.jetpack.service.db.dao;

import com.mikel.projectdemo.jetpack.service.model.RemoteKey;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface RemoteKeyDao {

    @Query("SELECT * FROM remote_key_table WHERE key_label = :label")
    RemoteKey getRemoteKey(int label);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRemoteKey(RemoteKey remoteKey);

    @Query("DELETE FROM remote_key_table WHERE key_label = :label")
    void deleteRemoteKey(int label);
}
