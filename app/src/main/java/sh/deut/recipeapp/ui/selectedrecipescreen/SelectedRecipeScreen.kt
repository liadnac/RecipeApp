package sh.deut.recipeapp.ui.selectedrecipescreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import sh.deut.recipeapp.R
import sh.deut.recipeapp.data.CookTime
import sh.deut.recipeapp.data.cookTimeFormater
import sh.deut.recipeapp.ui.RecipeViewModel
import sh.deut.recipeapp.ui.UiIngredient
import sh.deut.recipeapp.ui.UiInstruction
import sh.deut.recipeapp.ui.UiRecipe
import sh.deut.recipeapp.ui.UiRecipePart
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun SelectedRecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel = viewModel(),
    contentPadding: PaddingValues = PaddingValues(8.dp)
) {
    val recipeUiState by recipeViewModel.uiState.collectAsState()
    val recipe = recipeUiState.selectedRecipe ?: return
    SelectedRecipeLayout(
        modifier,
        recipe,
        { recipeId: Int, recipePartIndex: Int, ingredientIndex: Int, isChecked: Boolean ->
            recipeViewModel.updateRecipeIngredientSelectionState(
                recipeId, recipePartIndex, ingredientIndex, isChecked
            )
        },
        { recipeId: Int, recipePartIndex: Int, instructionIndex: Int, isChecked: Boolean ->
            recipeViewModel.updateRecipeInstructionSelectionState(
                recipeId, recipePartIndex, instructionIndex, isChecked
            )
        },
        contentPadding
    )
}

fun Modifier.onMeasureConstraints(
    block: (Constraints) -> Unit
) = layout { measurable, constraints ->
    // record the constraints *before* measuring so that they're available during recursive measurement
    block(constraints)
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.place(0, 0)
    }
}

fun Modifier.constrainSize(
    getConstraints: () -> Constraints
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints.constrain(getConstraints()))
    layout(placeable.width, placeable.height) {
        placeable.place(0, 0)
    }
}

@Composable
fun SelectedRecipeLayout(
    modifier: Modifier = Modifier,
    recipe: UiRecipe,
    updateRecipeIngredientSelectionState: (recipeId: Int, recipePartIndex: Int, ingredientIndex: Int, isChecked: Boolean) -> Unit,
    updateRecipeInstructionSelectionState: (recipeId: Int, recipePartIndex: Int, instructionIndex: Int, isChecked: Boolean) -> Unit,
    contentPadding: PaddingValues = PaddingValues(8.dp)
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { recipe.recipeParts.size })
    var constraints by remember { mutableStateOf(Constraints()) }

    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize()
            .onMeasureConstraints { constraints = it }
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        )

        Text(
            text = recipe.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(500)
                .data(recipe.imgUrl).build(),
            contentDescription = stringResource(R.string.recipe_image_content_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .size(220.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_small)),
        )

        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconAndTextColumn(
                title = "Total time",
                icon = R.drawable.clock_svgrepo_com,
                iconDescription = stringResource(R.string.clock_icon),
                mainText = cookTimeFormater(recipe.cookTime.readyIn)
            )
            IconAndTextColumn(
                title = "Prep time",
                icon = R.drawable.hat_chef_svgrepo_com,
                iconDescription = stringResource(R.string.prep_icon),
                mainText = cookTimeFormater(recipe.cookTime.handsOn)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainSize { constraints }
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage, modifier = Modifier
            ) {
                recipe.recipeParts.forEachIndexed { index, item ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        text = {
                            Text(
                                text = item.name, maxLines = 2, overflow = TextOverflow.Ellipsis
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    index
                                )
                            }
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                RecipePartTab(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    recipePart = recipe.recipeParts[page],
                    onIngredientCheckedChanged = { isChecked, inIndex ->
                        updateRecipeIngredientSelectionState(
                            recipe.id, pagerState.currentPage, inIndex, isChecked
                        )
                    },
                    onInstructionCheckedChanged = { isChecked, insIndex ->
                        updateRecipeInstructionSelectionState(
                            recipe.id, pagerState.currentPage, insIndex, isChecked
                        )
                    })
            }
        }
    }
}


@Composable
fun IconAndTextColumn(
    title: String, @DrawableRes icon: Int, iconDescription: String? = null, mainText: String

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
    val recipe = UiRecipe(
        888,
        "Example",
        "",
        "Just an example",
        CookTime(30.toDuration(DurationUnit.MINUTES), 45.toDuration(DurationUnit.MINUTES)),
        listOf(
            UiRecipePart(
                "A", listOf(
                    UiIngredient("flour"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg"),
                    UiIngredient("egg")
                ), listOf(
                    UiInstruction("mix"), UiInstruction("form")
                )
            ), UiRecipePart(
                "B", listOf(UiIngredient("flour"), UiIngredient("egg")), listOf(
                    UiInstruction("mix"), UiInstruction("form")
                )
            )
        ),
        11
    )

    SelectedRecipeLayout(
        Modifier,
        recipe,
        { _: Int, _: Int, _: Int, _: Boolean -> },
        { _: Int, _: Int, _: Int, _: Boolean -> })
}