package com.svg.dogsapp.data.repository

import com.svg.dogsapp.common.toDomainModel
import com.svg.dogsapp.data.local.DogImageDao
import com.svg.dogsapp.data.network.ApiService
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository

class DogImageRepositoryImpl(
    private val apiService: ApiService,
    private val dogImageDao: DogImageDao
) : DogImageRepository {

    override suspend fun getRandomDogImage(): DogImageModel {
        try {
            return apiService.getRandomDogImage().toDomainModel()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override suspend fun getRecentDogImages(): List<DogImageModel> {
        try {
            return dogImageDao.getAllDogImages()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}