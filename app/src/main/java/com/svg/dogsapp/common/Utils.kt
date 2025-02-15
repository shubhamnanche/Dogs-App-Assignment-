package com.svg.dogsapp.common

import android.graphics.Bitmap
import com.svg.dogsapp.data.model.DogDTO
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.utils.loadImageBitmap

fun DogDTO.toDomainModel(): DogImageModel {
    return DogImageModel(
        message = message ?: "",
        status = status ?: ""
    )
}

suspend fun DogImageModel.toBitmap(): Bitmap? {
    return loadImageBitmap(message)
}