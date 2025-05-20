package sh.deut.recipeapp.ui

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.CookTime
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.Recipe
import sh.deut.recipeapp.data.RecipePart
import sh.deut.recipeapp.data.SubCategory

data class RecipeUiState(
// Main screen ui state
    val categoryList: List<CategoryCardState> = emptyList(),

// Recipe browsing screen ui state
    val subcategoryName: String = "",
    val subcategoryRecipes: List<PartialRecipe> = emptyList(),

// Selected recipe screen ui state
    val selectedRecipeName: String = "",
    val selectedRecipe: UiRecipe? = null
)

data class CategoryCardState(
    val category: Category,
    val isExpanded: Boolean = false,
)

data class UiRecipe(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val description: String,
    val cookTime: CookTime,
    val recipeParts: List<UiRecipePart>,
    val subcategoryId: Int
)

data class UiRecipePart(
    val name: String,
    val ingredients: List<UiIngredient>,
    val instructions: List<UiInstruction>
)

data class UiIngredient(
    val ingredient: String,
    val isChecked: Boolean = false
)

data class UiInstruction(
    val instruction: String,
    val isChecked: Boolean = false
)