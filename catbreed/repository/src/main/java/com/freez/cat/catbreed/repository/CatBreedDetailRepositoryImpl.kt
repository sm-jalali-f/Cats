package com.freez.cat.catbreed.repository

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedDetailRepository
import com.freez.cat.core.util.Resource
import com.freez.cat.data.remote.theCatApi.CatApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatBreedDetailRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : CatBreedDetailRepository {
    override suspend fun getDetail(catId: String): Flow<Resource<CatBreed>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = catApiService.getCatBreedDetail(catId)

                if (response.isSuccessful) {
                    var catResponse = response.body()
                    if (catResponse != null) {
                        var mappedItem =
                            CatBreed(
                                id = catResponse.id,
                                name = catResponse.name,
                                image = catResponse.image,
                                origin = catResponse.origin,
                                countryCode = catResponse.country_code,
                                weight = catResponse.weight,
                                description = catResponse.description,
                                temperament = catResponse.temperament.split(",")
                                    .map { str -> str.trim() },
                                intelligence = catResponse.intelligence,
                                isFavorite = false
                            )
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