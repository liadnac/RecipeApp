package com.example.recipeapp.navigation

import androidx.annotation.StringRes
import com.example.recipeapp.R

enum class Destination(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    RecipeBrowsing(title = R.string.recipe_browse),
    SelectedRecipe(title = R.string.selected_recipe)

}