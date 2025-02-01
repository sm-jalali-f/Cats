package com.freez.cat.catbreed.repository

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.core.util.Constant
import com.freez.cat.core.util.Resource
import com.freez.cat.data.remote.theCatApi.CatApiService
import com.freez.cat.data.remote.theCatApi.entity.CatBreedResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatBreedListRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : CatBreedListRepository {
    override suspend fun getCatBreeds(page: Int): Flow<Resource<List<CatBreed>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = catApiService.getCatBreeds(page, Constant.PAGE_COUNT)

                if (response.isSuccessful) {
                    var catList = response.body()
                    if (catList != null) {
                        var mappedItem = catList.map { item ->

                            item.toCatBreed()
                        }
                        emit(Resource.Success(mappedItem))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
}

private fun CatBreedResponseItem.toCatBreed(): CatBreed {
    return CatBreed(
        id = this.id,
        name = this.name,
        image = this.image,
        origin = this.origin,
        countryCode = this.country_code,
        weight = this.weight,
        description = this.description,
        temperament = this.temperament.split(",")
            .map { str -> str.trim() },
        intelligence = this.intelligence,
        isFavorite = false
    )
}
