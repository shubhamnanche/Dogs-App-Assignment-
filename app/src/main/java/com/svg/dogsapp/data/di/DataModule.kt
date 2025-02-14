package com.svg.dogsapp.data.di

import com.svg.dogsapp.data.network.ApiService
import com.svg.dogsapp.data.network.BASE_URL
import com.svg.dogsapp.data.repository.DogImageRepositoryImpl
import com.svg.dogsapp.domain.repository.DogImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create<ApiService>(ApiService::class.java)
    }

    @Provides
    fun provideDogImageRepo(apiService: ApiService): DogImageRepository{
        return DogImageRepositoryImpl(apiService)
    }

}