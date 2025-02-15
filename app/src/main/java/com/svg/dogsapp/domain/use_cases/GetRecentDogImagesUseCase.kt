package com.svg.dogsapp.domain.use_cases

import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.repository.DogImageRepository

class GetRecentDogImagesUseCase(private val dogImageRepository: DogImageRepository) {
    suspend operator fun invoke(): List<DogImageModel> {
        return dogImageRepository.getRecentDogImages()
    }

}