package sh.deut.recipeapp.ui.mainscreen


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sh.deut.recipeapp.R
import sh.deut.recipeapp.data.Category
import sh.deut.recipeapp.data.SubCategory
import sh.deut.recipeapp.ui.CategoryCardState
import sh.deut.recipeapp.ui.RecipeViewModel


@Composable
fun CategoryDisplayScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel = viewModel(),
    subCategoryOnClick: (SubCategory) -> Unit,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {
    val recipeUiState by recipeViewModel.uiState.collectAsState()
    val categoryList = recipeUiState.categoryList
    CategoryList(
        categoryCardStateList = categoryList,
        subCategoryOnClick = subCategoryOnClick,
        modifier = modifier,
        contentPadding = contentPadding,
    )
}


@Composable
fun CategoryList(
    categoryCardStateList: List<CategoryCardState>,
    subCategoryOnClick: (SubCategory) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
) {

    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(categoryCardStateList) { category ->
            CategoryCard(
                category = category, modifier = Modifier, subCategoryOnClick = subCategoryOnClick
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
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        onClick = { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom

            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).crossfade(500)
                        .data(category.category.imgUrl).build(),
                    placeholder = painterResource(R.drawable.baking),
                    contentDescription = "An image of the category",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .alpha(0.7f)
                        .width(108.dp)
                )
                Text(
                    category.category.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer

                )
                Spacer(modifier = Modifier.weight(0.25f))
                IconButton(
                    modifier = Modifier
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
            if (expanded) {
                SubCategoryList(
                    subCategoryList = category.category.subcategoryList,
                    subCategoryOnClick = subCategoryOnClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
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
            HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { subCategoryOnClick(subCategory) })
                    .height(56.dp),
            ) {
                Text(
                    subCategory.name,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


val pancake: SubCategory = SubCategory(1, "Pancakes")
val popsicle: SubCategory = SubCategory(2, "Popsicles")
val kidsCategory: Category = Category(1, "Kids", "", listOf(pancake, popsicle))

@Preview
@Composable
fun CategoryCardPreview() {

    CategoryCard(
        category = CategoryCardState(category = kidsCategory),
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
            Category(
                1, "test", "", listOf(
                    SubCategory(12, "subsub"), SubCategory(13, "choco")
                )
            ),
            false,
        ), CategoryCardState(Category(2, "test2", "", listOf(SubCategory(15, "pancake"))), false)
    )
    CategoryList(
        subCategoryOnClick = {},
        contentPadding = PaddingValues(4.dp),
        categoryCardStateList = categoryCardStateList,

        )
}