package com.example.recipeapp.ui.selectedrecipescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.data.RecipePart

@Composable
fun RecipePartScreen(
    modifier: Modifier = Modifier,
    recipePart: RecipePart
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
            text = recipePart.name,
            style = MaterialTheme.typography.titleLarge
        )
        Row {
            Icon(
                painter = painterResource(R.drawable.water_container_svgrepo_com),
                contentDescription = stringResource(R.string.ingredients_icon),
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = stringResource(R.string.ingredients_sub_title),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
        }
        IngredientsList(ingredientsList = recipePart.ingredients)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer)))
        Row {
            Icon(
                painter = painterResource(R.drawable.break_eggs_svgrepo_com),
                contentDescription = stringResource(R.string.instruction_icon),
                modifier = Modifier
                    .size(size = 24.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = stringResource(R.string.instructions_sub_title),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
        }
        InstructionsList(instructionsList = recipePart.instructions)
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer)))
    }
}


@Composable
fun IngredientsList(
    modifier: Modifier = Modifier,
    ingredientsList: List<String>
) {
    Column(
        modifier = modifier
    ) {
        ingredientsList.forEach { ingredient ->
            RowWithCheckbox(text = ingredient)
        }
    }
}

@Composable
fun InstructionsList(
    modifier: Modifier = Modifier,
    instructionsList: List<String>
) {
    Column(
        modifier = modifier
    ) {
        instructionsList.forEachIndexed { index, instruction ->
            RowWithCheckbox(text = instruction, rowNumber = index + 1)
        }
    }
}

@Composable
fun RowWithCheckbox(
    modifier: Modifier = Modifier,
    text: String,
    rowNumber: Int? = null
) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier,
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
        val textDecoration = if (isChecked) {
            TextDecoration.LineThrough
        } else {
            TextDecoration.None
        }
        val displayText = rowNumber?.let { "$it. $text" } ?: text

        Text(
            modifier = Modifier,
            text = displayText,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = textDecoration,
            color = if (isChecked) Color.Gray else MaterialTheme.colorScheme.onBackground

        )

    }
}


@Preview(showBackground = true)
@Composable
fun RecipePartScreenPreview() {
    val exampleRecipePart =
        RecipePart(
            name = "Crust", ingredients = listOf("100g butter", "2 eggs"),
            instructions = listOf("Mix the butter with eggs", "Allow the dough to rest")
        )
    RecipePartScreen(recipePart = exampleRecipePart)
}

@Preview(showBackground = true)
@Composable
fun IngredientsListPreview() {
    val exampleList = listOf("100g butter", "2 eggs")
    IngredientsList(ingredientsList = exampleList)
}

@Preview(showBackground = true)
@Composable
fun IngredientRowPreview() {
    RowWithCheckbox(text = "100g butter")
}