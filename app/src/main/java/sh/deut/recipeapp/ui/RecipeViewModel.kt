package sh.deut.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sh.deut.recipeapp.RecipeApplication
import sh.deut.recipeapp.data.CategoryRepository
import sh.deut.recipeapp.data.PartialRecipe
import sh.deut.recipeapp.data.RecipeRepository
import sh.deut.recipeapp.data.SubCategory
import sh.deut.recipeapp.data.SubcategoryRepository

class RecipeViewModel(
    private val categoryRepository: CategoryRepository,
    private val subcategoryRepository: SubcategoryRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()
    private var selectedSubCategory: SubCategory? = null

    fun initializeCategories() {
        viewModelScope.launch {
            val categoryList = categoryRepository.getCategories()
            _uiState.update { currentState ->
                currentState.copy(
                    categoryList = categoryList.map {
                        CategoryCardState(
                            category = it
                        )
                    })
            }
        }
    }

    fun subcategorySelected(subCategory: SubCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                subcategoryName = subCategory.name
            )
        }
        selectedSubCategory = subCategory
    }

    fun initializeRecipes() {
        viewModelScope.launch {
            val recipesList = subcategoryRepository.getSubcategoryRecipes(
                subcategoryId = selectedSubCategory!!.id
            )
            _uiState.update { currentState ->
                currentState.copy(
                    subcategoryRecipes = recipesList
                )
            }
        }
    }

    fun updateSelectedRecipe(recipe: PartialRecipe) {
        viewModelScope.launch {
            val fullRecipe = recipeRepository.getSelectedRecipe(recipeId = recipe.id)
            _uiState.update { currentState ->
                currentState.copy(
                    selectedRecipeName = recipe.name, selectedRecipe = fullRecipe
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val categoryRepository = application.container.categoryRepository
                val subcategoryRepository = application.container.subcategoryRepository
                val recipeRepository = application.container.recipeRepository
                RecipeViewModel(
                    categoryRepository = categoryRepository,
                    subcategoryRepository = subcategoryRepository,
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}