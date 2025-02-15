package com.svg.dogsapp.utils

import android.graphics.Bitmap
import android.util.Log
import com.svg.dogsapp.common.toBitmap
import com.svg.dogsapp.common.toByteArray
import com.svg.dogsapp.data.local.DogDatabase
import com.svg.dogsapp.domain.model.DogImageEntity

class ImageLruCache(
    dogDatabase: DogDatabase,
    private val maxSize: Int
) {
    private val TAG = this::class.java.simpleName
    private val dogDao = dogDatabase.dogDao()

    suspend fun putBitmap(url: String, bitmap: Bitmap) {
        saveBitmapToDatabase(url, bitmap)
        enforceMaxSizeLimit()
    }

    private suspend fun enforceMaxSizeLimit() {
        val count = dogDao.getCount()
        if (count > maxSize) {
            dogDao.deleteOldest()
        }
    }

    private suspend fun saveBitmapToDatabase(url: String, bitmap: Bitmap) {
        val byteArray = bitmap.toByteArray()
        val entity = DogImageEntity(0, url, byteArray, System.currentTimeMillis())
        dogDao.insert(entity)
        Log.d(TAG, "saveBitmapToDatabase: success!")
        enforceMaxSizeLimit()
    }

    suspend fun getBitmap(url: String): Bitmap? {
        return dogDao.getByUrl(url)?.bitmapBytes?.toBitmap()
    }

    suspend fun clear() {
        dogDao.clear()
    }
}