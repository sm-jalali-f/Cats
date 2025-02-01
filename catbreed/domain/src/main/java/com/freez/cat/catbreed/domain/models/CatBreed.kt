package com.freez.cat.catbreed.domain.models

import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Weight

data class CatBreed(
    val id: String,
    val image: Image?,
    val name: String,
    val origin: String,
    val countryCode: String,
    val temperament: List<String>,
    val description: String,
    val weight: Weight,
    val intelligence: Int,
    val isFavorite: Boolean,
)