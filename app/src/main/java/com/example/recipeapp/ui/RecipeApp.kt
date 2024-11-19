package com.example.recipeapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeapp.data.Category
import com.example.recipeapp.data.categoryList
import com.example.recipeapp.ui.mainscreen.CategoryGrid
import com.example.recipeapp.ui.mainscreen.RecipeTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val categoryList: List<Category> = categoryList(context)
    Scaffold(
        topBar = {
            RecipeTopBar(
                scrollBehavior = scrollBehavior,
                searchOnClick = {},
                menuOnClick = {}
            )
        }
    ) { contentPadding ->
        CategoryGrid(categoryList,
            modifier
                .padding(contentPadding)
                .fillMaxSize())

    }
}

@Preview
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}