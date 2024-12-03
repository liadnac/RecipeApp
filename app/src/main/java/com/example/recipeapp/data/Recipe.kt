package com.example.recipeapp.data

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Recipe(
    val name: String,
    val cookTime: Duration
)
