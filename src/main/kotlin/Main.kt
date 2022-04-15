// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import data.RecipeRepository
import feature.home.HomeScreen
import feature.home.RecipesViewModel
import feature.recipe.RecipeDetailsScreen
import ui.common.navigation.Home
import ui.common.navigation.Nav
import ui.common.navigation.RecipeDetails
import ui.theme.RecipeekTheme

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun main() = application {
    val nav = rememberSaveable { mutableStateOf<Nav>(Home) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Recipeek",
        state = WindowState(
            width = 380.dp,
            height = 600.dp,
        ),
        onPreviewKeyEvent = { keyEvent ->
        when (keyEvent.key) {
            Key.Escape -> {
                nav.value = Home
                true
            }
            else -> false
        }
    }
    ) {
        App(nav)
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
@Preview
fun App(nav: MutableState<Nav>) {
    val repository = RecipeRepository()
    val viewModel = RecipesViewModel(repository)

    RecipeekTheme {
        val density = LocalDensity.current
        val threshold = density.run { 400.dp.toPx() }
        BoxWithConstraints {
            if (maxWidth.value > threshold) {
                TwoColumnsLayout(viewModel)
            } else {
                when(nav.value) {
                    Home -> {
                        HomeScreen(viewModel) { recipeId ->
                            nav.value = RecipeDetails(recipeId)
                        }
                    }
                    is RecipeDetails -> {
                        RecipeDetailsScreen(
                            recipeId = (nav.value as RecipeDetails).recipeId,
                            viewModel = viewModel
                        ) {
                            nav.value = Home
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun TwoColumnsLayout(viewModel: RecipesViewModel) {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.4f), contentAlignment = Alignment.Center) {
            HomeScreen(viewModel) {
                viewModel.getRecipe(it)
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            RecipeDetailsScreen(viewModel = viewModel)
        }
    }
}
