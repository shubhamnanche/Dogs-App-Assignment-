package com.svg.dogsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.domain.local.DogDao
import com.svg.dogsapp.domain.model.DogImageEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

@Dao
interface DogDao: DogDao {
    @Query("SELECT * FROM dogs ORDER BY timestamp DESC")
    override suspend fun getAll(): List<DogImageEntity>

    @Query("SELECT * FROM dogs WHERE url = :url")
    override suspend fun getByUrl(url: String): DogImageEntity?

    @Insert
    override suspend fun insert(dogImageEntity: DogImageEntity)

    @Query("DELETE FROM dogs WHERE url = :url")
    override suspend fun deleteByUrl(url: String)

    @Query("DELETE FROM dogs WHERE timestamp = (SELECT MIN(timestamp) FROM dogs)")
    override suspend fun deleteOldest()

    @Query("SELECT COUNT(*) FROM dogs")
    override suspend fun getCount(): Int

    @Query("DELETE FROM dogs")
    override suspend fun clear()
}