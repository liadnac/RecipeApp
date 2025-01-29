package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.data.CategoryRepository
import com.example.recipeapp.data.NetworkCategoryRepository
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.SubCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel( private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()
    private var selectedSubCategory: SubCategory? = null
    private val subcategoryToRecipesMap: MutableMap<SubCategory, List<PartialRecipe>> =
        mutableMapOf()

    private fun <T> mergeLists(vararg lists: List<T>): List<T> {
        val mergedList = mutableListOf<T>()
        for (list in lists) {
            mergedList.addAll(list)
        }
        return mergedList
    }

//    fun initializeCategories(categoryList: List<Category>) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                categoryList = categoryList.map {
//                    CategoryCardState(
//                        category = it
//                    )
//                }
//            )
//        }
//    }

    fun initializeCategories() {
        viewModelScope.launch {
            val categoryList = categoryRepository.getCategories()
            _uiState.update { currentState ->
                currentState.copy(
                    categoryList = categoryList.map {
                        CategoryCardState(
                            category = it
                        )
                    }
                )
            }
        }
    }


    // Temporary, delete later
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
                    subcategoryToRecipesMap[subcategory] = recipeList
                }
            }
        }
    }

    fun subcategorySelected(subCategory: String) {
        _uiState.update { currentState ->
            currentState.copy(
                subcategoryName = subCategory
            )
        }
        selectedSubCategory = subcategoryToRecipesMap.keys.find { it.name == subCategory }
    }

    fun initializeRecipes() {
        _uiState.update { currentState ->
            subcategoryToRecipesMap[selectedSubCategory]?.let {
                currentState.copy(
                    subcategoryRecipes = it
                )
            }!!
        }
    }

    fun updateSelectedRecipe(recipe: String, recipeList: List<Recipe>) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRecipeName = recipe,
//  Temporary way to check that the Ui state updates, should change!
                selectedRecipe = if (recipe.length > 15) {
                    recipeList[0]
                } else {
                    recipeList[1]
                }
            )
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val categoryRepository = application.container.categoryRepository
                RecipeViewModel(categoryRepository = categoryRepository)
            }
        }
    }
}