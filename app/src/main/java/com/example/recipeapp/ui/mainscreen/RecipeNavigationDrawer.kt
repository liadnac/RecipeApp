package com.example.recipeapp.ui.mainscreen

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable

@Composable
fun RecipeNavigationDrawer() {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {

            }
        }
    ) { }
}