package com.example.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration

@Serializable

/**
 * Data class for recipes in the recipe browsing screen.
 * Matches the format received from `/categories/{categoryId}/subcategories/{subcategoryId}/recipes`.
 */
data class PartialRecipe(
    val id: Int,
    val name: String,
    val cookTime: Duration,
    val subcategoryId: Int
)

/**
 * Data class for recipe displayed in full in the selected recipe screen.
 * Each recipe is made up of recipe parts.
 * Matches the format received from `/recipes/{recipeId}`.
 */
data class Recipe(
    val id: Int,
    val name: String,
    val cookTime: List<Duration>,
    val recipeParts: List<RecipePart>,
    val subcategoryId: Int
)

/**
 * Data class for recipe parts.
 * Each recipe part contains the name, ingredients and instructions for a part of the recipe.
 * For example, a pie recipe would contain a crust recipe part, with its' matching ingredients and instructions.
 * Matches the format received from `/recipes/{recipeId}`.
 */

data class RecipePart(
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

fun getPartialRecipesFromJsonFile(
    context: Context,
    fileName: String
): List<PartialRecipe> {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<List<PartialRecipe>>(jsonString)
}

fun getFullRecipeFromJsonFile(
    context: Context,
    fileName: String
): Recipe {
    val jsonString: String = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return Json.decodeFromString<Recipe>(jsonString)
}
