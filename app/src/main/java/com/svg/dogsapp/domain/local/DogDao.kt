package com.svg.dogsapp.domain.local

import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.domain.model.DogImageEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

interface DogDao {
    suspend fun getAll(): List<DogImageEntity>
    suspend fun getByUrl(url: String): DogImageEntity?
    suspend fun insert(dogImageEntity: DogImageEntity)
    suspend fun deleteByUrl(url: String)
    suspend fun deleteOldest()
    suspend fun getCount(): Int
    suspend fun clear()
}