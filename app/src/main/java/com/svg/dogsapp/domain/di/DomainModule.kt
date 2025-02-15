package com.svg.dogsapp.domain.di

import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.domain.use_cases.GetRandomDogImageUseCase
import com.svg.dogsapp.domain.use_cases.GetRecentDogImagesUseCase
import com.svg.dogsapp.utils.ImageLruCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Singleton
    @Provides
    fun provideGetRandomDogImageUseCase(dogImageRepository: DogImageRepository, imageLruCache: ImageLruCache): GetRandomDogImageUseCase {
        return GetRandomDogImageUseCase(dogImageRepository, imageLruCache)
    }

    @Singleton
    @Provides
    fun provideGetRecentDogImagesUseCase(dogImageRepository: DogImageRepository): GetRecentDogImagesUseCase {
        return GetRecentDogImagesUseCase(dogImageRepository)
    }

}