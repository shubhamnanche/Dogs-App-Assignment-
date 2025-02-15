package com.svg.dogsapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DogImageModel(
    val url: String
)