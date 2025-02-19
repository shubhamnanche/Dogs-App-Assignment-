package com.svg.dogsapp.presentation

import com.svg.dogsapp.domain.model.DogImageModel

data class DogImageState(
    val isLoading: Boolean = false,
    val data: DogImageModel? = null,
    val error: String = ""
)

data class DogImagesState(
    val isLoading: Boolean = false,
    val data: List<DogImageModel>? = null,
    val error: String = ""
)