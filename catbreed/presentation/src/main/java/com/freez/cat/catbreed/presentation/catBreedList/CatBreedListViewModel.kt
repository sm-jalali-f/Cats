package com.freez.cat.catbreed.presentation.catBreedList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.usecase.CatBreedListUseCase
import com.freez.cat.catbreed.domain.usecase.ToggleFavoriteCatUseCase
import com.freez.cat.core.util.Constant
import com.freez.cat.catbreed.domain.usecase.CatBreedListUseCase
import com.freez.cat.catbreed.domain.usecase.ToggleFavoriteCatUseCase
import com.freez.cat.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class CatBreedListViewModel @Inject constructor(
    private val catUseCase: CatBreedListUseCase,
    private val favoriteUseCase: ToggleFavoriteCatUseCase
) : ViewModel() {
    private var _catList = MutableStateFlow<List<CatBreed>>(emptyList())
    val catList: StateFlow<List<CatBreed>> get() = _catList

    private var _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private var currentPage = 0
    private var isLastPage = false
    private var retryNumber: AtomicInteger = AtomicInteger(0)
    private var maxRetryNumber = 3

    init {
        loadData()
    }

    private fun loadData() {
        if (_loadingState.value || isLastPage) return

        viewModelScope.launch(Dispatchers.IO) {
            catUseCase.invoke(currentPage).collect { result ->
                updateState(result)
            }
        }
    }

    fun loadMoreData() {
        loadData()
    }

    fun changeFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.invoke(catId, isFavorite)
        }
    }

    private suspend fun updateState(result: Resource<List<CatBreed>>) {
        when (result) {
            is Resource.Loading -> {
                _loadingState.value = true
            }

            is Resource.Success -> {
                result.data?.let { newData ->
                    updateData(newData)
                }
                _loadingState.value = false
                retryNumber.set(0)
            }

            is Resource.Error -> {
                _loadingState.value = false
                if (retryNumber.get() <= maxRetryNumber) {
                    _errorState.value =
                        "Failed, retry in 3 seconds automatically.try number= ${retryNumber.get() + 1}"
                    delay(1000)
                    retryNumber.incrementAndGet()
                    loadData()
                    return
                } else {
                    _errorState.value = Constant.FAILED_COMPLETELY
                }
            }
        }
    }

    private fun updateData(newData: List<CatBreed>) {
        if (newData.isNotEmpty()) {
            val currentList = _catList.value.toMutableList()

            newData.forEach { newCat ->
                val existingCatIndex =
                    currentList.indexOfFirst { it.id == newCat.id }
                if (existingCatIndex != -1) {
                    currentList[existingCatIndex] = newCat
                } else {
                    currentList.add(newCat)
                }
            }
            _catList.value = currentList
            currentPage++
        } else {
            isLastPage = true
        }
    }

}