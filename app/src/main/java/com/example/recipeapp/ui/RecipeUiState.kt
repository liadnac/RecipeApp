package com.example.recipeapp.ui

import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe

data class RecipeUiState (
    val subcategory: String = "",
    val subcategoryRecipes: List<PartialRecipe> = emptyList(),
    val selectedRecipeName: String = "",
    val selectedRecipe: Recipe? = null
)