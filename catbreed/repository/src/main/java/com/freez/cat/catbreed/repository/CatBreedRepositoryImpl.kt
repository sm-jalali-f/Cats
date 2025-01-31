package com.freez.cat.catbreed.repository

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedRepository
import com.freez.cat.core.util.Constant
import com.freez.cat.core.util.Resource
import com.freez.cat.data.remote.theCatApi.CatApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatBreedRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : CatBreedRepository {
    override suspend fun getCatBreeds(page: Int): Flow<Resource<List<CatBreed>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = catApiService.getCatBreeds(page, Constant.PAGE_COUNT)

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        emit(Resource.Success(response.body()!!.map { item ->
                            CatBreed(
                                id = item.id,
                                name = item.name,
                                image = item.image,
                                origin = item.origin,
                                countryCode = item.country_code,
                                weight = item.weight,
                                description = item.description,
                                temperament = item.temperament,
                                intelligence = item.intelligence
                            )
                        }))
                    } else {
                        Resource.Error(response.message(), "body Response is null")
                    }
                } else {
                    Resource.Error(response.message(), "Response is unsuccessful")
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", e)
            }
        }
}