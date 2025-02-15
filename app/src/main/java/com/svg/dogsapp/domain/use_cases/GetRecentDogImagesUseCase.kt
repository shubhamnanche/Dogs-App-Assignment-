package com.svg.dogsapp.domain.use_cases

import android.util.Log
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecentDogImagesUseCase(private val dogImageRepository: DogImageRepository) {
    private val TAG = this::class.java.simpleName

    operator fun invoke(): Flow<Resource<List<DogImageModel>>> = flow {
        emit(Resource.Loading())

        try {
            val dogImages = dogImageRepository.getRecentDogImages()
            emit(Resource.Success(data = dogImages))
            Log.d(TAG, "Recent Images: ${dogImages.size}")
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
            Log.d(TAG, "Error: ${e.message}")
        }
    }

}