package com.svg.dogsapp.domain.use_cases

import android.util.Log
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.common.compressToByteArray
import com.svg.dogsapp.common.toBitmap
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.utils.ImageLruCache
import com.svg.dogsapp.utils.loadImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomDogImageUseCase(
    private val dogImageRepository: DogImageRepository,
    private val imageLruCache: ImageLruCache
) {
    private val TAG = this::class.java.simpleName

    operator fun invoke(): Flow<Resource<DogImageModel>> = flow {
        emit(Resource.Loading())

        try {
            val dogImage = dogImageRepository.getRandomDogImage()
            emit(Resource.Success(data = dogImage))
            loadImage(dogImage.url, imageLruCache)?.let {
                imageLruCache.putBitmap(dogImage.url,
                    it.compressToByteArray(80).toBitmap()
                )
            }
            Log.d(TAG, "Image data: ${dogImage.url}")
            Log.d(TAG, "Cache Size: ${dogImageRepository.getRecentDogImages().size}")
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
            Log.d(TAG, "Error: ${e.message}")
        }
    }

}