package com.example.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration

@Serializable
data class Recipe(
    val name: String,
    val cookTime: Duration
)

fun getRecipeFromJsonFile(
    context: Context,
    fileName: String
): List<Recipe> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<Recipe>>(jsonString)
}
