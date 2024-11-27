package com.example.recipeapp.ui.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(modifier: Modifier = Modifier,
                 scrollBehavior: TopAppBarScrollBehavior,
                 searchOnClick: () -> Unit,
                 menuOnClick: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            IconButton(onClick = searchOnClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search button"
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = menuOnClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu button"
                )
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    RecipeTopBar(
        scrollBehavior = scrollBehavior,
        searchOnClick = {},
        menuOnClick = {}
    )
}