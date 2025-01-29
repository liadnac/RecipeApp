package com.example.recipeapp.ui

import com.example.recipeapp.data.Category
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.SubCategory

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