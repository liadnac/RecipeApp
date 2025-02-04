package com.example.recipeapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.getFullRecipeFromJsonFile
import com.example.recipeapp.navigation.Destination
import com.example.recipeapp.ui.mainscreen.CategoryDisplayScreen
import com.example.recipeapp.ui.mainscreen.RecipeTopBar
import com.example.recipeapp.ui.recipebrowsescreen.RecipeBrowsingScreen
import com.example.recipeapp.ui.selectedrecipescreen.SelectedRecipeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Destination.valueOf(
        backStackEntry?.destination?.route ?: Destination.Start.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val recipeList: MutableList<Recipe> =
        mutableListOf(getFullRecipeFromJsonFile(context, "pumpkinPieRecipe.json"))
    recipeList.add(getFullRecipeFromJsonFile(context, "ChocolatePancakesRecipe.json"))

    val recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory)
    recipeViewModel.initializeCategories()

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
        ) {
            composable(route = Destination.Start.name) {
                CategoryDisplayScreen(
                    recipeViewModel = recipeViewModel,
                    subCategoryOnClick = { subCategory ->
                        recipeViewModel.subcategorySelected(subCategory)
                        navController.navigate(Destination.RecipeBrowsing.name)
                    },
                    contentPadding = contentPadding,
                    modifier = Modifier
                )
            }

            composable(route = Destination.RecipeBrowsing.name) {
                RecipeBrowsingScreen(
                    recipeViewModel = recipeViewModel,
                    modifier = Modifier,
                    contentPadding = contentPadding,
                    recipeOnClick = { recipe ->
                        recipeViewModel.updateSelectedRecipe(
                            recipe = recipe.name,
                            recipeList = recipeList,
                        )
                        navController.navigate(Destination.SelectedRecipe.name)
                    }
                )
            }

            composable(route = Destination.SelectedRecipe.name) {
                SelectedRecipeScreen(
                    recipeViewModel = recipeViewModel,
                    modifier = Modifier,
                    contentPadding = contentPadding,
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