package com.example.nav3.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String,
    val image_url: String?
)
