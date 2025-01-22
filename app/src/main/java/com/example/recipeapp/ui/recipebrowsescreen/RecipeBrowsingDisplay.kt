package com.example.recipeapp.ui.recipebrowsescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.cookTimeFormater
import com.example.recipeapp.ui.RecipeViewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun RecipeBrowsingScreen(
    recipeViewModel: RecipeViewModel = viewModel(),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    recipeOnClick: (PartialRecipe) -> Unit,
) {
    val recipeUiState by recipeViewModel.uiState.collectAsState()
    recipeViewModel.initializeRecipes()

    RecipeBrowsingGrid(
        modifier = modifier,
        contentPadding = contentPadding,
        recipeOnClick = recipeOnClick,
        subcategoryName = recipeUiState.subcategoryName,
        subcategoryRecipes = recipeUiState.subcategoryRecipes
    )
}

@Composable
fun RecipeBrowsingGrid(
    subcategoryName: String,
    subcategoryRecipes: List<PartialRecipe>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    recipeOnClick: (PartialRecipe) -> Unit,
) {

    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(144.dp),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.Center,
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Text(
                text = "Recipes for $subcategoryName",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                textAlign = TextAlign.Start

            )
        }
        items(subcategoryRecipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                recipeOnClick = { recipeOnClick(recipe) }
            )

        }
    }
}


@Composable
fun RecipeCard(
    recipe: PartialRecipe,
    recipeOnClick: (PartialRecipe) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth(),
        onClick = { recipeOnClick(recipe) }

    ) {

        Box {
            Image(
                painter = painterResource(R.drawable.pasta),
                contentDescription = stringResource(R.string.recipe_image_content_desc)
            )

        }
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painterResource(R.drawable.clock_svgrepo_com),
                contentDescription = stringResource(R.string.clock_icon),
                modifier = Modifier.size(25.dp)
            )
            Text(
                cookTimeFormater(recipe.cookTime),
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.padding_small)
                    )
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Text(
            text = recipe.name,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Preview
@Composable
fun RecipeCardPreview() {
    val recipe = PartialRecipe(1, "Pumpkin Pancakes", 30.toDuration(DurationUnit.MINUTES), 1)
    RecipeCard(recipe, {})
}


@Preview(showBackground = true)
@Composable
fun RecipeBrowsingGridPreview() {
    RecipeBrowsingGrid(
        recipeOnClick = {},
        subcategoryName = "Pancakes",
        subcategoryRecipes = listOf(
            PartialRecipe(
            11, "Choco pancake", 30.toDuration(DurationUnit.MINUTES),
                subcategoryId = 12
            ),
            PartialRecipe(
                15, "Yogurt pancake", 30.toDuration(DurationUnit.MINUTES),
                subcategoryId = 12
            )
        ),

    )
}