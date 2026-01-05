package com.example.nav3.data.mapper

import com.example.nav3.data.local.BeerEntity
import com.example.nav3.domain.model.Beer

fun Beer.toBeerEntity(): BeerEntity{

    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        first_brewed = first_brewed,
        description = description,
        image_url = image

)
}


fun BeerEntity.toBeer(): Beer{

    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        first_brewed = first_brewed,
        description = description,
        image = image_url?:""

    )
}
