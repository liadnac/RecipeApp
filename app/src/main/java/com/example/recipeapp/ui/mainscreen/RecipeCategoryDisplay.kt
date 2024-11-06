package com.example.recipeapp.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryGrid(
    categoryList: List<String>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = categoryList, key = { category -> category }) { category ->
            CategoryCard(category, modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .aspectRatio(1.5f))
        }
    }
}

@Composable
fun CategoryCard(category: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(category, modifier = Modifier.padding(4.dp))
    }
}


@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard("Kids", modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1.5f))
}

@Preview
@Composable
fun CategoryGridPreview() {
    val categoryList: List<String> = listOf("Kids","Pasta","Bread and more")
    CategoryGrid(
        categoryList,
        contentPadding = PaddingValues(4.dp)
    )
}