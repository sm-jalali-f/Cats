package com.freez.cat.catbreed.domain.repository

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface CatBreedRepository {

    suspend fun getCatBreeds(page: Int): Flow<Resource<List<CatBreed>>>
}