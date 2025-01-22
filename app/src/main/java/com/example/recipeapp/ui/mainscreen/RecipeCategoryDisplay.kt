package com.example.recipeapp.ui.mainscreen


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.Category
import com.example.recipeapp.data.SubCategory
import com.example.recipeapp.data.getCategoriesFromJsonFile
import com.example.recipeapp.ui.CategoryCardState
import com.example.recipeapp.ui.RecipeViewModel

@Composable
fun CategoryDisplayScreen(
    recipeViewModel: RecipeViewModel = viewModel(),
    subCategoryOnClick: (SubCategory) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {
    val recipeUiState by recipeViewModel.uiState.collectAsState()
    val categoryList = recipeUiState.categoryList
    CategoryGrid(
        categoryCardStateList = categoryList,
        subCategoryOnClick = subCategoryOnClick,
        modifier = modifier,
        contentPadding = contentPadding,
    )
}

@Composable
fun CategoryGrid(
    categoryCardStateList: List<CategoryCardState>,
    subCategoryOnClick: (SubCategory) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 144.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.SpaceAround,

        ) {
        items(categoryCardStateList) { category ->
            CategoryCard(
                category = category,
                modifier = Modifier,
                subCategoryOnClick = subCategoryOnClick
            )
        }


    }
}

@Composable
fun CategoryCard(
    category: CategoryCardState,
    subCategoryOnClick: (SubCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = ""
    )


    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .animateContentSize(), // This is where the magic happens!
        onClick = { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()

        ) {
            Box(

            ) {
                Image(
                    painter = painterResource(R.drawable.baking),
                    contentDescription = "An image of the category",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    category.category.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray

                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.size(24.dp))
                SubCategoryList(
                    subCategoryList = category.subcategories,
                    subCategoryOnClick = subCategoryOnClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.size(24.dp))
            }
            IconButton(modifier = Modifier
                .alpha(0.2f)
                .rotate(rotationState), onClick = {
                expanded = !expanded
            }) {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Show more",
                )
            }


        }
    }
}


@Composable
fun SubCategoryList(
    subCategoryList: List<SubCategory>,
    subCategoryOnClick: (SubCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        subCategoryList.forEach { subCategory ->
            HorizontalDivider()
            Row(
                modifier = Modifier
            ) {
                TextButton(
                    onClick = { subCategoryOnClick(subCategory) },
                    modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_medium)
                    ),
                ) {
                    Text(
                        subCategory.name, textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }
}

val pancake: SubCategory = SubCategory(1, "Pancakes", 1)
val popsicle: SubCategory = SubCategory(2, "Popsicles", 1)
val kidsCategory: Category = Category(1, "Kids")

@Preview
@Composable
fun CategoryCardPreview() {

    CategoryCard(
        category = CategoryCardState(category = Category(1, "Test")),
        subCategoryOnClick = {},
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun SubCategoryListPreview() {
    val subCategoryList: List<SubCategory> = listOf(pancake, popsicle)
    SubCategoryList(subCategoryList = subCategoryList, subCategoryOnClick = {})
}

@Preview
@Composable
fun CategoryGridPreview() {
    val context = LocalContext.current
    val categoryCardStateList = listOf(
        CategoryCardState(
            Category(1, "test"), false, listOf(
                SubCategory(12, "subsub", 1), SubCategory
                    (13, "choco", 1)
            )
        ), CategoryCardState(Category(2,"test2"),false, listOf(SubCategory(15,"pancake",2)))
    )
    CategoryGrid(
        subCategoryOnClick = {},
        contentPadding = PaddingValues(4.dp),
        categoryCardStateList = categoryCardStateList,

        )
}