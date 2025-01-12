package com.example.recipeapp.ui

import com.example.recipeapp.data.PartialRecipe

data class RecipeUiState (
    val subcategory: String = "asdf",
    val recipes: List<PartialRecipe> = emptyList()
)