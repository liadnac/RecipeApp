package sh.deut.recipeapp.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.temporal.TemporalAmount
import kotlin.time.Duration



/**
 * Data class for recipes in the recipe browsing screen.
 * Matches the format received from `/categories/{categoryId}/subcategories/{subcategoryId}/recipes`.
 */
@Serializable
data class PartialRecipe(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val cookTime: Duration,
    val subcategoryId: Int
)

/**
 * Data class for recipe displayed in full in the selected recipe screen.
 * Each recipe is made up of recipe parts.
 * Matches the format received from `/recipes/{recipeId}`.
 */
@Serializable
data class Recipe(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val description: String,
    val cookTime: CookTime,
    val recipeParts: List<RecipePart>,
    val subcategoryId: Int
)

/**
 * Cook time for the full recipe displayed in the selected recipe screen.
 * Matches the format of `cookTime` received from `/recipes/{recipeId}`.
 *  @property handsOn the time require for the preparation of the recipe
 *  @property readyIn the total time it takes until the dish is ready
 */
@Serializable
data class CookTime(
    val handsOn: Duration,
    val readyIn: Duration
)

/**
 * Data class for recipe parts.
 * Each recipe part contains the name, ingredients and instructions for a part of the recipe.
 * For example, a pie recipe would contain a crust recipe part, with its matching ingredients and instructions.
 * Matches the format received from `/recipes/{recipeId}`.
 */
@Serializable
data class RecipePart(
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

//@Serializable
//data class Ingredient(
//    val amount: Float,
//    val ingredient: String
//)

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

fun cookTimeFormater(
    cookTime: Duration
): String {
    var formatedCookTime = ""
    val splitCookTime = cookTime.toString().split(
        "h", "m"
    )
    if (cookTime.toString().contains("h")) {
        formatedCookTime += splitCookTime[0] + " hr"
        formatedCookTime += splitCookTime[1] + " mins"
    } else if (cookTime.toString().contains("m")) {
        formatedCookTime += splitCookTime[0] + " mins"
    }
    return formatedCookTime
}