package com.svg.dogsapp.data.di

import com.svg.dogsapp.data.local.DogImageDao
import com.svg.dogsapp.data.network.ApiService
import com.svg.dogsapp.data.network.BASE_URL
import com.svg.dogsapp.data.repository.DogImageRepositoryImpl
import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.utils.ImageLruCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideImageLruCache(): ImageLruCache {
        return ImageLruCache(maxSize = 1024 * 1024 * 100) //100MB cache size
    }

    @Singleton
    @Provides
    fun provideDogImageDao(imageLruCache: ImageLruCache): DogImageDao {
        return DogImageDao(imageLruCache)
    }

    @Singleton
    @Provides
    fun provideDogImageRepo(apiService: ApiService, dogImageDao: DogImageDao): DogImageRepository {
        return DogImageRepositoryImpl(apiService, dogImageDao)
    }


}