package com.svg.dogsapp.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svg.dogsapp.common.Resource
import com.svg.dogsapp.data.local.DogDatabase
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.domain.use_cases.GetRandomDogImageUseCase
import com.svg.dogsapp.domain.use_cases.GetRecentDogImagesUseCase
import com.svg.dogsapp.utils.ImageLruCache
import com.svg.dogsapp.utils.loadImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogImageViewModel @Inject constructor(
    private val getRandomDogImageUseCase: GetRandomDogImageUseCase,
    private val getRecentDogImagesUseCase: GetRecentDogImagesUseCase,
    private val imageLruCache: ImageLruCache,
    private val dogDatabase: DogDatabase
) :
    ViewModel() {
    private val TAG = this::class.java.simpleName

    private val dogDao = dogDatabase.dogDao()

    private val _dogImage = mutableStateOf(DogImageState())
    val dogImage: State<DogImageState> get() = _dogImage

    private val _recentlyGeneratedDogs = mutableStateOf(DogImagesState())
    val recentlyGeneratedDogs: State<DogImagesState> get() = _recentlyGeneratedDogs

    private val imageCache = mutableStateMapOf<String, Bitmap?>()

    fun getRandomDogImage() {
        viewModelScope.launch {
            getRandomDogImageUseCase().collect {
                when (it) {
                    is Resource.Error -> _dogImage.value =
                        DogImageState(error = it.message.toString())

                    is Resource.Loading -> _dogImage.value = DogImageState(isLoading = true)
                    is Resource.Success ->
                        _dogImage.value = DogImageState(data = it.data)
                }
            }
        }
    }

    fun getRecentlyGeneratedDogs() {
        viewModelScope.launch {
            getRecentDogImagesUseCase().collect {
                when (it) {
                    is Resource.Error -> _recentlyGeneratedDogs.value =
                        DogImagesState(error = it.message.toString())

                    is Resource.Loading -> _recentlyGeneratedDogs.value =
                        DogImagesState(isLoading = true)

                    is Resource.Success -> _recentlyGeneratedDogs.value =
                        DogImagesState(data = it.data)
                }
            }
        }
    }

    fun clearRecentlyGeneratedDogs() {
        viewModelScope.launch {
            dogDao.clear()
            _recentlyGeneratedDogs.value = DogImagesState(data = emptyList())
            Log.d(TAG, "clearRecentlyGeneratedDogs: cleared all!")
        }
    }

    fun loadImageBitmap(dogImageModel: DogImageModel) {
        if (!imageCache.containsKey(dogImageModel.url)) {
            viewModelScope.launch(Dispatchers.IO) {
                val bitmap = loadImage(dogImageModel.url, imageLruCache)
                imageCache[dogImageModel.url] = bitmap
            }
        }
    }

    fun getBitmapFromCache(url: String): Bitmap? = imageCache[url]


}