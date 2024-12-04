package com.example.recipeapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.getRecipeFromJsonFile
import com.example.recipeapp.ui.mainscreen.RecipeTopBar
import com.example.recipeapp.ui.recipebrowsescreen.RecipeBrowsingGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val recipeList: List<Recipe> = getRecipeFromJsonFile(context, "recipe.json")
    Scaffold(
        topBar = {
            RecipeTopBar(
                scrollBehavior = scrollBehavior,
                searchOnClick = {},
                menuOnClick = {}
            )
        }
    ) { contentPadding ->
        RecipeBrowsingGrid(
            recipeList = recipeList,
            contentPadding = contentPadding,
            modifier = modifier
        )

    }
}

@Preview
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}