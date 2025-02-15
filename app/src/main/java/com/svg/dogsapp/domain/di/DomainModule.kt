package com.svg.dogsapp.domain.di

import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.domain.use_cases.GetRandomDogImageUseCase
import com.svg.dogsapp.domain.use_cases.GetRecentDogImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideGetRandomDogImageUseCase(dogImageRepository: DogImageRepository): GetRandomDogImageUseCase {
        return GetRandomDogImageUseCase(dogImageRepository)
    }

    @Provides
    fun provideGetRecentDogImagesUseCase(dogImageRepository: DogImageRepository): GetRecentDogImagesUseCase {
        return GetRecentDogImagesUseCase(dogImageRepository)
    }

}