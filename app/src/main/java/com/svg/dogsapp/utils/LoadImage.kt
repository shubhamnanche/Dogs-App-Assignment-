package com.svg.dogsapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun loadImage(url: String, imageLruCache: ImageLruCache): Bitmap? {
    val cachedBitmap = imageLruCache.getBitmap(url)
    if (cachedBitmap != null) {
        return cachedBitmap
    }

    return withContext(Dispatchers.IO) {
        val bitmap = loadImageBitmap(url)
        if (bitmap != null) {
            imageLruCache.putBitmap(url, bitmap)
        }
        bitmap
    }
}

suspend fun loadImageBitmap(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}