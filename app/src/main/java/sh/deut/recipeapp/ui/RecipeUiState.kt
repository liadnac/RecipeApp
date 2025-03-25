package sh.deut.recipeapp.ui

import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.Recipe
import sh.deut.recipeapp.data.SubCategory

data class RecipeUiState(
// Main screen ui state
    val categoryList: List<CategoryCardState> = emptyList(),

// Recipe browsing screen ui state
    val subcategoryName: String = "",
    val subcategoryRecipes: List<PartialRecipe> = emptyList(),

// Selected recipe screen ui state
    val selectedRecipeName: String = "",
    val selectedRecipe: Recipe? = null
)

data class CategoryCardState(
    val category: Category,
    val isExpanded: Boolean = false,
)