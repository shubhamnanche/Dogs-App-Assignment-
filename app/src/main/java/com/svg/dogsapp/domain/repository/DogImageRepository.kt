package com.svg.dogsapp.domain.repository

import com.svg.dogsapp.domain.model.DogImageModel

interface DogImageRepository {

    suspend fun getRandomDogImage(): DogImageModel

}