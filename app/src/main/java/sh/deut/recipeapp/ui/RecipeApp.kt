package sh.deut.recipeapp.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import sh.deut.recipeapp.navigation.Destination
import sh.deut.recipeapp.ui.mainscreen.CategoryDisplayScreen
import sh.deut.recipeapp.ui.mainscreen.RecipeTopBar
import sh.deut.recipeapp.ui.recipebrowsescreen.RecipeBrowsingScreen
import sh.deut.recipeapp.ui.selectedrecipescreen.SelectedRecipeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {


        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = Destination.valueOf(
            backStackEntry?.destination?.route ?: Destination.Start.name
        )
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(250)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(250)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(250)) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(250)) },
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
                                recipe = recipe,
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
}

@Preview
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}