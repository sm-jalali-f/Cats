package com.freez.cat.catbreed.repository

import android.util.Log
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.CombineCatFavoriteRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CombineCatFavoriteRepositoryImpl @Inject constructor(
    private val catBreedListRepository: CatBreedListRepository,
    private val favoriteCatRepository: FavoriteCatRepository,
): CombineCatFavoriteRepository {
    override suspend fun getCatBreeds(page: Int): Flow<Resource<List<CatBreed>>> {
        val catBreedsFlow = catBreedListRepository.getCatBreeds(page)

        val favoriteCatIdsFlow = favoriteCatRepository.getFavoriteCats()
        return combine(catBreedsFlow, favoriteCatIdsFlow) { catBreedsResource, favoriteCatIds ->
            when (catBreedsResource) {
                is Resource.Success -> {

                    Resource.Success(catBreedsResource.data!!.map { catBreed ->
                        catBreed.copy(
                            isFavorite = favoriteCatIds.contains(catBreed.id)
                        )
                    }
                    )
                }

                is Resource.Error -> {
                    return@combine catBreedsResource
                }

                is Resource.Loading -> {
                    Resource.Loading()
                }

            }
        }
    }
}