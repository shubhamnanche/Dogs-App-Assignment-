package com.svg.dogsapp.domain.use_cases

import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomDogImageUseCase(private val dogImageRepository: DogImageRepository) {

    operator fun invoke(): Flow<Resource<DogImageModel>> = flow<Resource<DogImageModel>> {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(data = dogImageRepository.getRandomDogImage()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unexpected error occurred"))
        }
    }

}