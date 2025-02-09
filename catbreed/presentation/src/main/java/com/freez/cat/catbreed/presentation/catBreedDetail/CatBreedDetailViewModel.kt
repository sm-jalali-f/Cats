package com.freez.cat.catbreed.presentation.catBreedDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.usecase.CatBreedDetailUseCase
import com.freez.cat.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedDetailViewModel @Inject constructor(
    private val catBreedDetailUseCase: CatBreedDetailUseCase,

) : ViewModel() {

    private var _catBreed: MutableStateFlow<CatBreed?> = MutableStateFlow(null)
    val catBreed: StateFlow<CatBreed?> get() = _catBreed

    private var _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private var _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> get() = _imageUrl

    fun getCatBreedDetail(catBreedId: String) {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            catBreedDetailUseCase(catBreedId).collect { result ->
                updateState(result)
                println(catBreed.value)
            }
        }

    }

    private fun updateState(result: Resource<CatBreed>) {
        when (result) {
            is Resource.Loading -> {
                _loadingState.value = true
            }

            is Resource.Success -> {
                result.data?.let { newData ->
                    _catBreed.value = newData
                }
                _loadingState.value = false
            }

            is Resource.Error -> {
                _errorState.value = result.message
                _loadingState.value = false
            }
        }
    }

    fun initialize(catBreedId: String, url: String) {
        _imageUrl.value = url
        getCatBreedDetail(catBreedId)
    }
}