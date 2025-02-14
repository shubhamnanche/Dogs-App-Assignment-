package com.svg.dogsapp.data.network

import com.svg.dogsapp.data.model.DogDTO
import retrofit2.http.GET

const val BASE_URL = "https://dog.ceo"

interface ApiService {

    //https://dog.ceo/api/breeds/image/random

    @GET("/api/breeds/image/random")
    suspend fun getRandomDog(): DogDTO

}