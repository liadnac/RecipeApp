package com.example.recipeapp

import com.example.recipeapp.data.SubCategory
import com.example.recipeapp.ui.RecipeViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewModelUnitTest {
    private val recipeViewModel: RecipeViewModel = RecipeViewModel()
    @Test
    fun testViewModelUpdateSubcategory() {
        val subCategory = SubCategory(id = 1, name = "pancake", categoryId = 1)
        recipeViewModel.subcategorySelected(subCategory)
        assertEquals("pancake", recipeViewModel.uiState.value.subcategoryName)
    }

}
