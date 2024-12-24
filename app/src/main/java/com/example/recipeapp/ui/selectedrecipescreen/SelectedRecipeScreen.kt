package com.example.recipeapp.ui.selectedrecipescreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.cookTimeFormater
import com.example.recipeapp.data.getFullRecipeFromJsonFile

@Composable
fun SelectedRecipeScreen(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    contentPadding: PaddingValues = PaddingValues(8.dp),
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = recipe.description,
            style = MaterialTheme.typography.bodyLarge,
        )
        Image(
            painter = painterResource(R.drawable.pumpkin_pie),
            contentDescription = stringResource(R.string.recipe_image_content_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .padding(dimensionResource(R.dimen.padding_small)),
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconAndTextColumn(
                title = "Total time",
                icon = R.drawable.ic_clock_svgrepo_com,
                iconDescription = stringResource(R.string.clock_icon),
                mainText = cookTimeFormater(recipe.cookTime.readyIn)
            )
            IconAndTextColumn(
                title = "Prep time",
                icon = R.drawable._74_bakery_kitchen_baking_cooking_1024,
                iconDescription = stringResource(R.string.prep_icon),
                mainText = cookTimeFormater(recipe.cookTime.handsOn)
            )

        }
        recipe.recipeParts.forEach { recipePart ->
            RecipePartScreen(recipePart = recipePart)
        }

    }
}

@Composable
fun IconAndTextColumn(
    title: String,
    @DrawableRes
    icon: Int,
    iconDescription: String? = null,
    mainText: String

) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title
        )
        Icon(
            painter = painterResource(icon),
            contentDescription = iconDescription,
            modifier = Modifier
                .size(48.dp)
                .padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = mainText
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer)))
    }
}


@Preview(showBackground = true)
@Composable
fun SelectedRecipeScreenPreview() {
    val context = LocalContext.current
    val recipe = getFullRecipeFromJsonFile(context, fileName = "pumpkinPieRecipe.json")
    SelectedRecipeScreen(recipe = recipe)
}