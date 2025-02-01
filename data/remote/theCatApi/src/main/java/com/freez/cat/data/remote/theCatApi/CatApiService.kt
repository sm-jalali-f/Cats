package com.freez.cat.data.remote.theCatApi

import com.freez.cat.data.remote.theCatApi.entity.CatBreedResponse
import com.freez.cat.data.remote.theCatApi.entity.CatBreedResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApiService {

    @Headers("Content-Type: application/json")
    @GET("v1/breeds")
    suspend fun getCatBreeds(
        @Query("page") page: Int? = 0,
        @Query("limit") limit: Int? = 20,
    ): Response<CatBreedResponse>

    @Headers("Content-Type: application/json")
    @GET("v1/breeds/:breed_id")
    suspend fun getCatBreedDetail(
        @Query("breed_id") catBreedId: String,
    ): Response<CatBreedResponseItem>
}