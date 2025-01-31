package com.freez.cat.catbreed.domain.models

import com.freez.cat.core.util.CatImage
import com.freez.cat.core.util.Weight

class CatBreed(
    val id: String,
    val image: CatImage,
    val name: String,
    val origin: String,
    val countryCode: String,
    val temperament: String,
    val description: String,
    val weight: Weight,
    val intelligence: Int
)