package com.svg.dogsapp.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.common.toBitmap
import com.svg.dogsapp.data.local.DogImageDao
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.use_cases.GetRandomDogImageUseCase
import com.svg.dogsapp.domain.use_cases.GetRecentDogImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DogImageViewModel @Inject constructor(
    private val getRandomDogImageUseCase: GetRandomDogImageUseCase,
    private val getRecentDogImagesUseCase: GetRecentDogImagesUseCase,
    private val dogImageDao: DogImageDao
) :
    ViewModel() {
    private val TAG = this::class.java.simpleName

    private val _dogImage = mutableStateOf(DogImageState())
    val dogImage: State<DogImageState> get() = _dogImage

    private val _recentlyGeneratedDogs = MutableStateFlow<List<DogImageModel>>(emptyList())
    val recentlyGeneratedDogs: StateFlow<List<DogImageModel>> get() = _recentlyGeneratedDogs.asStateFlow()

    fun getRandomDogImage() {
        viewModelScope.launch {
            getRandomDogImageUseCase().collect {
                when (it) {
                    is Resource.Error -> _dogImage.value =
                        DogImageState(error = it.message.toString())

                    is Resource.Loading -> _dogImage.value = DogImageState(isLoading = true)
                    is Resource.Success -> {
                        _dogImage.value = DogImageState(data = it.data)
                        it.data?.let {
                            dogImageDao.addDogImage(it)
                        }
                    }
                }
            }
        }
    }

    fun getRecentlyGeneratedDogs() {
        viewModelScope.launch {
            _recentlyGeneratedDogs.value = getRecentDogImagesUseCase()
        }
    }

    fun loadImageBitmap(dogImageModel: DogImageModel): Bitmap? {
        return runBlocking(Dispatchers.IO) {
            dogImageModel.toBitmap()
        }
    }

    fun clearRecentlyGeneratedDogs() {
        viewModelScope.launch {
            dogImageDao.clearDogImages()
            _recentlyGeneratedDogs.value = emptyList()
            Log.d(TAG, "clearRecentlyGeneratedDogs: cleared all!")
        }
    }


}