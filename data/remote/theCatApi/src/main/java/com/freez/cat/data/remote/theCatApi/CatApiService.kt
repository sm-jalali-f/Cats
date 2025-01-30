package com.freez.cat.data.remote.theCatApi

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApiService {

    @Headers("Content-Type: application/json")
    @GET("videos/v1/images/search")
    suspend fun searchImage(
        @Query("size") size: String? = "med",
        @Query("mime_types") mimeTypes: String? = "jpg",
        @Query("format") format: String? = "json",
        @Query("has_breeds") hasBreed: Boolean? = true,
        @Query("order") order: String? = "RANDOM",
        @Query("page") page: Int? = 0,
        @Query("limit") limit: Int? = 20,
    )
}