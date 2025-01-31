package com.freez.cat.catbreed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.cat.catbreed.domain.FavoriteCatUseCase
import com.freez.cat.catbreed.domain.GetCatBreedUseCase
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catUseCase: GetCatBreedUseCase,
    private val favoriteUseCase: FavoriteCatUseCase,
) : ViewModel() {
    private var _catList = MutableStateFlow<List<CatBreed>>(emptyList())
    val catList: StateFlow<List<CatBreed>> get() = _catList

    private var _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private var currentPage = 0
    private var isLastPage = false

    private fun loadData() {
        if (_loadingState.value || isLastPage) return

        viewModelScope.launch(Dispatchers.IO) {
            catUseCase.execute(currentPage).collect { result ->
                updateState(result)
            }
        }
    }

    fun loadMoreData() {
        loadData()
    }

    fun onSearchQueryChanged(search: String) {
        // TODO: Search Name
    }

    fun changeFavorite(catId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.changeCatFavorite(catId, isFavorite)
        }
    }

    private fun updateState(result: Resource<List<CatBreed>>) {
        when (result) {
            is Resource.Loading -> {
                _loadingState.value = true
            }

            is Resource.Success -> {
                result.data?.let { newData ->
                    updateData(newData)
                }
                _loadingState.value = false
            }

            is Resource.Error -> {
                _errorState.value = result.message
                _loadingState.value = false
            }
        }
    }

    private fun updateData(newData: List<CatBreed>) {
        if (newData.isNotEmpty()) {
            val currentList = _catList.value.toMutableList()

            newData.forEach { newCat ->
                val existingCatIndex =
                    currentList.indexOfFirst { it.id == newCat.id }
                if (existingCatIndex != -1) { // update exist data
                    currentList[existingCatIndex] = newCat
                } else {
                    currentList.add(newCat) //add new data
                }
            }
            _catList.value = currentList
            currentPage++
        } else {
            isLastPage = true
        }
    }

}