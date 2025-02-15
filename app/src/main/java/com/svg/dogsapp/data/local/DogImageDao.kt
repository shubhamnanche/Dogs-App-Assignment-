package com.svg.dogsapp.data.local

import android.util.Log
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.utils.ImageLruCache
import com.svg.dogsapp.utils.loadImageBitmap
import javax.inject.Inject

class DogImageDao @Inject constructor(private val imageLruCache: ImageLruCache) {
    private val TAG = this::class.java.simpleName

    fun getAllDogImages(): List<DogImageModel> {
        val dogs = mutableListOf<DogImageModel>()
        val keys = imageLruCache.getKeys()
        for (key in keys) {
            val dogImageModel = DogImageModel(key, "success")
            dogs.add(dogImageModel)
        }
        return dogs
    }

    fun clearDogImages() {
        imageLruCache.clear()
    }

    suspend fun addDogImage(dogImageModel: DogImageModel) {
        loadImageBitmap(dogImageModel.message)?.let { bitmap ->
            imageLruCache.putBitmap(dogImageModel.message, bitmap)
            Log.d(TAG, "addDogImage: size > ${getAllDogImages().size}")
        }
    }
}