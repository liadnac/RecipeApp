package com.example.recipeapp.data

import android.content.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration

@Serializable
/*
Data class for recipes in the recipe browsing screen.
Matches the format for get all recipes in subcategory in the REST api.
 */
data class Recipe(
    val id: Int,
    val name: String,
    @SerialName("cook_time")
    val cookTime: Duration,
    @SerialName("subcategory_id")
    val subcategoryId: Int
)

data class FullRecipe(
    val id: Int,
    val name: String,
    @SerialName("cook_time")
    val cookTime: List<Duration>,
    val recipeParts: List<RecipePart>,
    @SerialName("subcategory_id")
    val subcategoryId: Int
)

data class RecipePart(
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

fun getRecipesFromJsonFile(
    context: Context,
    fileName: String
): List<Recipe> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<Recipe>>(jsonString)
}

fun getFullRecipeFromJsonFile(
    context: Context,
    fileName: String
): FullRecipe {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<FullRecipe>(jsonString)
}
