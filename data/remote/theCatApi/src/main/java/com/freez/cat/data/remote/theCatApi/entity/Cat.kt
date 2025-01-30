package com.freez.cat.data.remote.theCatApi.entity

data class Cat(
    val breeds: List<BreedResponse>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)