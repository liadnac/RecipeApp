package com.example.recipeapp

import com.example.recipeapp.fake.FakeDataSource
import com.example.recipeapp.fake.FakeNetworkCategoryRepository
import com.example.recipeapp.fake.FakeNetworkRecipeRepository
import com.example.recipeapp.fake.FakeNetworkSubcategoryRepository
import com.example.recipeapp.rules.TestDispatcherRule
import com.example.recipeapp.ui.RecipeViewModel
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
}