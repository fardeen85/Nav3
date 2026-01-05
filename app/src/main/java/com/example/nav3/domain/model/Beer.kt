package com.example.nav3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    val id: Int=0,
    val name: String="",
    val tagline: String="",
    val first_brewed: String="",
    val description: String="",
    val image: String="",
    val abv: Double=0.0,
    val ibu: Double=0.0,
    val target_fg: Double=0.0,
    val target_og: Double=0.0,
    val ebc: Double=0.0,
    val srm: Double=0.0,
    val ph: Double? = null,
    val attenuation_level: Double? = 0.0,
    val volume: Volume?=null,
    val boil_volume: Volume? = null,
    val method: Method? = null,
    val ingredients: Ingredients? = null,
    val food_pairing: List<String> = emptyList(),
    val brewers_tips: String="",
    val contributed_by: String = ""
)

@Serializable
data class Volume(
    val value: Double,
    val unit: String
)

@Serializable
data class Method(
    val mash_temp: List<MashTemp>,
    val fermentation: Fermentation,
    val twist: String? = null
)

@Serializable
data class MashTemp(
    val temp: Temp,
    val duration: Double?=null
)

@Serializable
data class Fermentation(
    val temp: Temp
)

@Serializable
data class Temp(
    val value: Double?=null,
    val unit: String
)

@Serializable
data class Ingredients(
    val malt: List<Malt>,
    val hops: List<Hop>,
    val yeast: String?=null
)

@Serializable
data class Malt(
    val name: String,
    val amount: Amount
)

@Serializable
data class Hop(
    val name: String,
    val amount: Amount,
    val add: String,
    val attribute: String
)

@Serializable
data class Amount(
    val value: Double,
    val unit: String
)
