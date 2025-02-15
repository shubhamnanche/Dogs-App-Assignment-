package com.svg.dogsapp.domain.use_cases

import android.util.Log
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomDogImageUseCase(private val dogImageRepository: DogImageRepository) {
    private val TAG = this::class.java.simpleName

    operator fun invoke(): Flow<Resource<DogImageModel>> = flow {
        emit(Resource.Loading())

        try {
            val dogImage = dogImageRepository.getRandomDogImage()
            emit(Resource.Success(data = dogImage))
            Log.d(TAG, "Loading Image: ${dogImage.status}")
            Log.d(TAG, "Image data: ${dogImage.message}")
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
            Log.d(TAG, "Error: ${e.message}")
        }
    }

}