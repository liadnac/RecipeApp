package com.example.recipeapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.data.Category
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.getCategoriesFromJsonFile
import com.example.recipeapp.data.getRecipesFromJsonFile
import com.example.recipeapp.navigation.Destination
import com.example.recipeapp.ui.mainscreen.CategoryGrid
import com.example.recipeapp.ui.mainscreen.RecipeTopBar
import com.example.recipeapp.ui.recipebrowsescreen.RecipeBrowsingGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Destination.valueOf(
        backStackEntry?.destination?.route ?: Destination.Start.name)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val categoryList: List<Category> = getCategoriesFromJsonFile(context, "categories.json")
    val recipeList: List<Recipe> = getRecipesFromJsonFile(context, "pancakes_recipes.json")
    Scaffold(
        topBar = {
            RecipeTopBar(
                scrollBehavior = scrollBehavior,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                searchOnClick = {},
                menuOnClick = {}
            )
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Start.name,
            modifier = modifier,
        ){
            composable(route = Destination.Start.name) {
                CategoryGrid(
                    categoryList = categoryList,
                    subCategoryOnClick = { navController.navigate(Destination.RecipeBrowsing.name) },
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }

            composable(route = Destination.RecipeBrowsing.name) {
                RecipeBrowsingGrid(
                    recipeList = recipeList,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }
        }


    }
}

@Preview
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}