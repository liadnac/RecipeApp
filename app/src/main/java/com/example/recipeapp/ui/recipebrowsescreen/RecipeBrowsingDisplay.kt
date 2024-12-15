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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.data.PartialRecipe
import com.example.recipeapp.data.getPartialRecipesFromJsonFile
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Composable
fun RecipeBrowsingGrid(
    recipeList: List<PartialRecipe>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = 132.dp),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        items(recipeList) { recipe ->
            RecipeCard(
                recipe,
            )

        }
    }
}

@Composable
fun RecipeCard(
    recipe: PartialRecipe,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth(),

        ) {

        Box {
            Image(
                painter = painterResource(R.drawable.pasta),
                contentDescription = "An image of the recipe"
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
                painterResource(R.drawable.ic_clock_svgrepo_com),
                contentDescription = "clock icon",
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


fun cookTimeFormater(
    cookTime: Duration
): String {
    var formatedCookTime = ""
    val splitCookTime = cookTime.toString().split(
        "h", "m"
    )
    if (cookTime.toString().contains("h")) {
        formatedCookTime += splitCookTime[0] + " hr"
        formatedCookTime += splitCookTime[1] + " mins"
    } else if (cookTime.toString().contains("m")) {
        formatedCookTime += splitCookTime[0] + " mins"
    }
    return formatedCookTime
}


@Preview
@Composable
fun RecipeCardPreview() {
    val recipe = PartialRecipe(1,"Pumpkin Pancakes", 30.toDuration(DurationUnit.MINUTES), 1)
    RecipeCard(recipe)
}


@Preview
@Composable
fun RecipeBrowsingGridPreview() {
    val context = LocalContext.current
    val recipeList: List<PartialRecipe> = getPartialRecipesFromJsonFile(
        context,
        fileName = "pancakesRecipes.json"
    )
    RecipeBrowsingGrid(recipeList)
}