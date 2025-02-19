package com.svg.dogsapp.data.repository

import com.svg.dogsapp.common.toDomainModel
import com.svg.dogsapp.data.local.DogDatabase
import com.svg.dogsapp.data.network.ApiService
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository

class DogImageRepositoryImpl(
    private val apiService: ApiService,
    private val dogDatabase: DogDatabase
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
            return dogDatabase.dogDao().getAll().map { it.toDomainModel() }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

}