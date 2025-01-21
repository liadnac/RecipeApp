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
import com.example.recipeapp.data.Category
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.SubCategory
import com.example.recipeapp.data.getCategoriesFromJsonFile
import com.example.recipeapp.data.getFullRecipeFromJsonFile
import com.example.recipeapp.data.getPartialRecipesFromJsonFile
import com.example.recipeapp.data.getSubcategoriesFromJsonFile
import com.example.recipeapp.navigation.Destination
import com.example.recipeapp.ui.mainscreen.CategoryGrid
import com.example.recipeapp.ui.mainscreen.RecipeTopBar
import com.example.recipeapp.ui.recipebrowsescreen.RecipeBrowsingGrid
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
    val categoryList: List<Category> = getCategoriesFromJsonFile(context, "categories.json")
    val kidsSubCategoryList: List<SubCategory> =
        getSubcategoriesFromJsonFile(context, "kidsSubcategories.json")
    val bakingSubCategoryList: List<SubCategory> =
        getSubcategoriesFromJsonFile(context, "bakingSubcategories.json")
    val mealsSubCategoryList: List<SubCategory> =
        getSubcategoriesFromJsonFile(context, "mealsSubcategories.json")
    val pancakeRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "pancakesRecipes.json")
    val frozenConfectionsRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "frozenConfectionsRecipes.json")
    val breakfastRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "breakfastRecipes.json")
    val lunchRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "lunchRecipes.json")
    val dinnerRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "dinnerRecipes.json")
    val breadAndDoughRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "breadAndDoughRecipes.json")
    val cakeAndMuffinsRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "cakeAndMuffinsRecipes.json")
    val cookiesAndBrowniesRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "cookiesAndBrowniesRecipes.json")
    val tartsAndPiesRecipeList: List<PartialRecipe> =
        getPartialRecipesFromJsonFile(context, "tartsAndPiesRecipes.json")
    val recipeList: MutableList<Recipe> = mutableListOf(getFullRecipeFromJsonFile(context, "pumpkinPieRecipe.json"))
    recipeList.add(getFullRecipeFromJsonFile(context, "ChocolatePancakesRecipe.json"))

    val recipeViewModel: RecipeViewModel = viewModel()
    recipeViewModel.setCategorySubcategoryMap(
        categoryList,
        kidsSubCategoryList,
        mealsSubCategoryList,
        bakingSubCategoryList,
    )
    recipeViewModel.setSubcategoryRecipesMap(
        kidsSubCategoryList,
        mealsSubCategoryList,
        bakingSubCategoryList,
        dinnerRecipeList,
        lunchRecipeList,
        breakfastRecipeList,
        breadAndDoughRecipeList,
        cakeAndMuffinsRecipeList,
        cookiesAndBrowniesRecipeList,
        pancakeRecipeList,
        tartsAndPiesRecipeList,
        frozenConfectionsRecipeList
    )
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
                CategoryGrid(
                    recipeViewModel = recipeViewModel,
                    subCategoryOnClick = { subCategory ->
                        recipeViewModel.subcategorySelected(subCategory.name)
                        navController.navigate(Destination.RecipeBrowsing.name)
                    },
                    contentPadding = contentPadding,
                    modifier = Modifier
                )
            }

            composable(route = Destination.RecipeBrowsing.name) {
                RecipeBrowsingGrid(
                    recipeViewModel = recipeViewModel,
                    modifier = Modifier,
                    contentPadding = contentPadding,
                    recipeOnClick = { recipe ->
                        recipeViewModel.updateSelectedRecipe(
                            recipe = recipe.name,
                            recipeList = recipeList,
                        )
                        navController.navigate(Destination.SelectedRecipe.name) }
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