package com.svg.dogsapp.data.di

import android.content.Context
import com.svg.dogsapp.data.local.DogDatabase
import com.svg.dogsapp.data.network.ApiService
import com.svg.dogsapp.data.network.BASE_URL
import com.svg.dogsapp.data.repository.DogImageRepositoryImpl
import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.utils.ImageLruCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideImageLruCache(
        dogDatabase: DogDatabase
    ): ImageLruCache {
        return ImageLruCache(
            dogDatabase = dogDatabase,
            maxSize = 20 //at the most 20 will be stored
        )
    }

    @Singleton
    @Provides
    fun provideDogDatabase(@ApplicationContext context: Context): DogDatabase {
        return DogDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDogImageRepo(apiService: ApiService, dogDatabase: DogDatabase): DogImageRepository {
        return DogImageRepositoryImpl(apiService, dogDatabase)
    }


}