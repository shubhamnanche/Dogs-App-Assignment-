package com.svg.dogsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DogDTO(
    val message: String?,
    val status: String?
)