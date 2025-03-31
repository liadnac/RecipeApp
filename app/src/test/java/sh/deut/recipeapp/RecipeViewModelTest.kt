package sh.deut.recipeapp

import sh.deut.recipeapp.fake.FakeDataSource
import sh.deut.recipeapp.fake.FakeNetworkCategoryRepository
import sh.deut.recipeapp.fake.FakeNetworkRecipeRepository
import sh.deut.recipeapp.fake.FakeNetworkSubcategoryRepository
import sh.deut.recipeapp.rules.TestDispatcherRule
import sh.deut.recipeapp.ui.RecipeViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RecipeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun recipeViewModel_getCategories_verifyRecipeUiStateSuccess() = runTest {
        val recipeViewModel = RecipeViewModel(
            categoryRepository = FakeNetworkCategoryRepository(),
            subcategoryRepository = FakeNetworkSubcategoryRepository(),
            recipeRepository = FakeNetworkRecipeRepository()
        )
        recipeViewModel.initializeCategories()
        assertEquals(FakeDataSource.categoryList.find { it.id == 1 },
            recipeViewModel.uiState.value.categoryList.find { it.category.id == 1 }?.category
        )
    }

    @Test
    fun recipeViewModel_subcategorySelected_verifyRecipeUiStateSuccess() = runTest {
        val recipeViewModel = RecipeViewModel(
            categoryRepository = FakeNetworkCategoryRepository(),
            subcategoryRepository = FakeNetworkSubcategoryRepository(),
            recipeRepository = FakeNetworkRecipeRepository()
        )
        recipeViewModel.subcategorySelected(
            subCategory = FakeDataSource.selectedSubCategory
        )
        assertEquals(FakeDataSource.selectedSubCategory.name,
            recipeViewModel.uiState.value.subcategoryName
        )
    }

    @Test
    fun recipeViewModel_initializeRecipes_verifyRecipeUiStateSuccess() = runTest {
        val recipeViewModel = RecipeViewModel(
            categoryRepository = FakeNetworkCategoryRepository(),
            subcategoryRepository = FakeNetworkSubcategoryRepository(),
            recipeRepository = FakeNetworkRecipeRepository()
        )
        recipeViewModel.subcategorySelected(
            subCategory = FakeDataSource.selectedSubCategory
        )
        recipeViewModel.initializeRecipes()
        assertEquals(FakeDataSource.subcategoryRecipeList,
            recipeViewModel.uiState.value.subcategoryRecipes
        )
    }

    @Test
    fun recipeViewModel_updateSelectedRecipe_verifyRecipeUiStateSuccess() = runTest {
        val recipeViewModel = RecipeViewModel(
            categoryRepository = FakeNetworkCategoryRepository(),
            subcategoryRepository = FakeNetworkSubcategoryRepository(),
            recipeRepository = FakeNetworkRecipeRepository()
        )
        recipeViewModel.updateSelectedRecipe(
            recipe = FakeDataSource.recipe1
        )
        assertEquals(FakeDataSource.fullRecipeBasedOnRecipe1,
            recipeViewModel.uiState.value.selectedRecipe
        )
    }
}