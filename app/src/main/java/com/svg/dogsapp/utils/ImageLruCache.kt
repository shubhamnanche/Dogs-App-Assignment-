package com.svg.dogsapp.utils

import android.graphics.Bitmap
import android.util.LruCache

data class CacheModel(
    val bitmap: Bitmap,
    val timestamp: Long
)

class ImageLruCache(private val maxSize: Int) {

    private val cache: LruCache<String, CacheModel> =
        object : LruCache<String, CacheModel>(maxSize) {
            override fun sizeOf(key: String, value: CacheModel): Int {
                return value.bitmap.byteCount
            }
        }

    fun getBitmap(url: String): Bitmap? {
        val cacheModel = cache.get(url)
        return cacheModel.bitmap
    }

    fun putBitmap(url: String, bitmap: Bitmap) {
        val cacheModel = CacheModel(bitmap, System.currentTimeMillis())
        cache.put(url, cacheModel)
        removeLRU(20)
    }

    fun removeLRU(keepSize: Int) {
        val currentValues = cache.snapshot().toList()
        val currentSize = currentValues.size
        if (currentSize > keepSize) {
            currentValues.sortedBy { it.second.timestamp }.take(currentSize - keepSize).forEach {
                cache.remove(it.first)
            }
        }
    }

    fun getKeys(): Set<String> {
        return cache.snapshot().keys
    }

    fun clear() {
        cache.evictAll()
    }
}