package com.svg.dogsapp.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.svg.dogsapp.data.model.DogDTO
import com.svg.dogsapp.domain.model.DogImageEntity
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.utils.ImageLruCache
import com.svg.dogsapp.utils.loadImage
import com.svg.dogsapp.utils.loadImageOnline
import java.io.ByteArrayOutputStream

fun DogDTO.toDomainModel(): DogImageModel {
    return DogImageModel(
        url = message ?: ""
    )
}

fun DogImageEntity.toDomainModel(): DogImageModel {
    return DogImageModel(
        url = this.url
    )
}

suspend fun DogImageModel.toBitmap(): Bitmap? {
    return loadImageOnline(url)?.toByteArray()?.toBitmap()
}

fun Bitmap.compressToByteArray(maxSizeKB: Int = 500): ByteArray {
    val stream = ByteArrayOutputStream()
    var quality = 100
    do {
        stream.reset()
        this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        quality -= 10
    } while (stream.toByteArray().size / 1024 > maxSizeKB && quality > 0)
    return stream.toByteArray()
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    stream.use {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
        return it.toByteArray()
    }
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

