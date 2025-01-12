package com.example.recipeapp.ui.recipebrowsescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.cookTimeFormater
import com.example.recipeapp.data.getPartialRecipesFromJsonFile
import com.example.recipeapp.ui.RecipeViewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Composable
fun RecipeBrowsingGrid(
    recipeViewModel: RecipeViewModel = viewModel(),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    recipeOnClick: () -> Unit,
) {
    val recipeUiState by recipeViewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = 132.dp),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        recipeViewModel.updateSubcategoryRecipes(recipeUiState.subcategory)
        items(recipeUiState.recipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                recipeOnClick = recipeOnClick
            )

        }
    }
}


@Composable
fun RecipeCard(
    recipe: PartialRecipe,
    recipeOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth(),
        onClick = recipeOnClick

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
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Text(
            text = recipe.name,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
            overflow = TextOverflow.Visible,
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
    val context = LocalContext.current
    val recipeList: List<PartialRecipe> = getPartialRecipesFromJsonFile(
        context,
        fileName = "pancakesRecipes.json"
    )
    RecipeBrowsingGrid(recipeOnClick = {})
}