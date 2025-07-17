package sh.deut.recipeapp.ui.selectedrecipescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import sh.deut.recipeapp.R
import sh.deut.recipeapp.ui.UiIngredient
import sh.deut.recipeapp.ui.UiInstruction
import sh.deut.recipeapp.ui.UiRecipePart

@Composable
fun RecipePartTab(
    modifier: Modifier = Modifier,
    recipePart: UiRecipePart,
    onInstructionCheckedChanged: (Boolean, Int) -> Unit,
    onIngredientCheckedChanged: (Boolean, Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
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
        IngredientsList(
            ingredientsList = recipePart.ingredients,
            onIngredientCheckedChanged = onIngredientCheckedChanged
        )
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
        InstructionsList(
            instructionsList = recipePart.instructions,
            onInstructionCheckedChanged = onInstructionCheckedChanged
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer)))
    }
}


@Composable
fun IngredientsList(
    modifier: Modifier = Modifier,
    ingredientsList: List<UiIngredient>,
    onIngredientCheckedChanged: (Boolean, Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ingredientsList.forEachIndexed { index, ingredient ->
            RowWithCheckbox(
                text = ingredient.ingredient,
                isChecked = ingredient.isChecked,
                onCheckedChanged = { onIngredientCheckedChanged(it, index) })
        }
    }
}

@Composable
fun InstructionsList(
    modifier: Modifier = Modifier,
    instructionsList: List<UiInstruction>,
    onInstructionCheckedChanged: (Boolean, Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        instructionsList.forEachIndexed { index, instruction ->
            RowWithCheckbox(
                text = instruction.instruction,
                isChecked = instruction.isChecked,
                rowNumber = index + 1,
                onCheckedChanged = { onInstructionCheckedChanged(it, index) })
        }
    }
}

@Composable
fun RowWithCheckbox(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    rowNumber: Int? = null
) {
    Row(
        modifier = modifier.clickable(
            onClick = { onCheckedChanged(isChecked.not()) },

            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier, checked = isChecked, onCheckedChange = {
                onCheckedChanged(it)
            })
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


fun exampleOnChecked(isChecked: Boolean, index: Int) {}

@Preview(showBackground = true)
@Composable
fun RecipePartScreenPreview() {
    val exampleRecipePart = UiRecipePart(
        name = "Crust",
        ingredients = listOf(UiIngredient("100g butter"), UiIngredient("2 eggs")),
        instructions = listOf(
            UiInstruction("Mix the butter with eggs"), UiInstruction("Allow the dough to rest")
        )
    )

    RecipePartTab(
        recipePart = exampleRecipePart,
        modifier = Modifier,
        onInstructionCheckedChanged = { bool, index -> exampleOnChecked(bool, index) },
        onIngredientCheckedChanged = { bool, index -> exampleOnChecked(bool, index) })
}

@Preview(showBackground = true)
@Composable
fun IngredientsListPreview() {
    val exampleList = listOf(UiIngredient("100g butter"), UiIngredient("2 eggs"))
    IngredientsList(
        ingredientsList = exampleList,
        modifier = Modifier,
        onIngredientCheckedChanged = { bool, index -> exampleOnChecked(bool, index) })
}

@Preview(showBackground = true)
@Composable
fun IngredientRowPreview() {
    RowWithCheckbox(
        text = "100g butter", modifier = Modifier, isChecked = true, onCheckedChanged = {})
}