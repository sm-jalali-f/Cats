package com.freez.cat.catbreed.repository

import android.util.Log
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.core.util.Constant
import com.freez.cat.core.util.Resource
import com.freez.cat.data.remote.theCatApi.CatApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatBreedListRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : CatBreedListRepository {
    override suspend fun getCatBreeds(page: Int): Flow<Resource<List<CatBreed>>> =
        flow {
            Log.d("THE-CAT_API", "CatBreedListRepositoryImpl: ")
            emit(Resource.Loading())
            try {
                val response = catApiService.getCatBreeds(page, Constant.PAGE_COUNT)

                if (response.isSuccessful) {
                    var catList = response.body()
                    if (catList != null) {
                        var mappedItem = catList.map { item ->
                            CatBreed(
                                id = item.id,
                                name = item.name,
                                image = item.image,
                                origin = item.origin,
                                countryCode = item.country_code,
                                weight = item.weight,
                                description = item.description,
                                temperament = item.temperament.split(",")
                                    .map { str -> str.trim() },
                                intelligence = item.intelligence,
                                isFavorite = false
                            )
                        }
                        emit(Resource.Success(mappedItem))
                    } else {
                        Resource.Error(response.message(), "body Response is null")
                    }
                } else {
                    Resource.Error(response.message(), "Response is unsuccessful")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", e)
            }
        }
}