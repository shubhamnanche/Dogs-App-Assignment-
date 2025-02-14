package com.svg.dogsapp.domain.di

import com.svg.dogsapp.domain.repository.DogImageRepository
import com.svg.dogsapp.domain.use_cases.GetRandomDogImageUseCase
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
}