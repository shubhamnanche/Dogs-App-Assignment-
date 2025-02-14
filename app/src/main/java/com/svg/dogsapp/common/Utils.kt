package com.svg.dogsapp.common

import com.svg.dogsapp.data.model.DogDTO
import com.svg.dogsapp.domain.model.DogImageModel

fun DogDTO.toDomainModel(): DogImageModel {
    return DogImageModel(
        message = this.message,
        status = this.status
    )
}