package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.SubCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()
    private val subcategoryToRecipesMap: MutableMap<String, List<PartialRecipe>> = mutableMapOf()

    private fun <T> mergeLists(vararg lists: List<T>): List<T> {
        val mergedList = mutableListOf<T>()
        for (list in lists) {
            mergedList.addAll(list)
        }
        return mergedList
    }

    fun setSubcategoryRecipesMap(
        subCategoryList1: List<SubCategory>,
        subCategoryList2: List<SubCategory>,
        subCategoryList3: List<SubCategory>,
        vararg recipeLists: List<PartialRecipe>,
    ) {
        val allSubCategories = mergeLists(subCategoryList1, subCategoryList2, subCategoryList3)
        for (subcategory in allSubCategories) {
            for (recipeList in recipeLists) {
                if (subcategory.id == recipeList[0].subcategoryId) {
                    subcategoryToRecipesMap[subcategory.name] = recipeList
                }
            }
        }
    }

    fun updateSubcategory(subCategory: SubCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                subcategory = subCategory.name
            )
        }
    }

    fun updateSubcategoryRecipes(subCategory: String) {
        _uiState.update { currentState ->
            subcategoryToRecipesMap[subCategory]?.let {
                currentState.copy(
                    recipes = it
                )
            }!!
        }
    }
}