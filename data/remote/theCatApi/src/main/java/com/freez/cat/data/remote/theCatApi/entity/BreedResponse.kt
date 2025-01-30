package com.freez.cat.data.remote.theCatApi.entity

import com.google.gson.annotations.SerializedName

data class BreedResponse(
    val name: String,
    val description: String,
    val natural: Int,
    val origin: String,
    val rare: Int,
    val rex: Int,
    val experimental: Int,
    val grooming: Int,
    val hairless: Int,
    val hypoallergenic: Int,
    val id: String,
    val indoor: Int,
    val intelligence: Int,
    val lap: Int?,
    val temperament: String,
    val vocalisation: Int,
    val weight: Weight,

    @SerializedName("adaptability")
    val adaptability: Int,

    @SerializedName("affection_level")
    val affection_level: Int,

    @SerializedName("alt_names")
    val alt_names: String,

    @SerializedName("cfa_url")
    val cfaUrl: String?,

    @SerializedName("child_friendly")
    val childFriendly: Int,

    @SerializedName("country_code")
    val countryCode: String,

    @SerializedName("country_codes")
    val countryCodes: String,

    @SerializedName("dog_friendly")
    val dogFriendly: Int,

    @SerializedName("energy_level")
    val energyLevel: Int,

    @SerializedName("health_issues")
    val healthIssues: Int,

    @SerializedName("life_span")
    val lifeSpan: String,

    @SerializedName("reference_image_id")
    val referenceImageId: String,

    @SerializedName("shedding_level")
    val sheddingLevel: Int,

    @SerializedName("short_legs")
    val shortLegs: Int,

    @SerializedName("social_needs")
    val socialNeeds: Int,

    @SerializedName("stranger_friendly")
    val strangerFriendly: Int,

    @SerializedName("suppressed_tail")
    val suppressedTail: Int,

    @SerializedName("vcahospitals_url")
    val vcahospitalsUrl: String?,

    @SerializedName("vetstreet_url")
    val vetstreetUrl: String?,

    @SerializedName("wikipedia_url")
    val wikipediaUrl: String

)