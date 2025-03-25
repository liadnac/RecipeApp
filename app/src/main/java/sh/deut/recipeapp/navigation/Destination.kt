package sh.deut.recipeapp.navigation

import androidx.annotation.StringRes
import sh.deut.recipeapp.R

enum class Destination(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    RecipeBrowsing(title = R.string.recipe_browse),
    SelectedRecipe(title = R.string.selected_recipe)

}