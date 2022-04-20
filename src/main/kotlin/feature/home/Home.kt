package feature.home

import SearchInput
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.Recipe
import ui.common.atom.AsyncImage
import ui.common.atom.loadImageBitmap
import ui.theme.AppColorsTheme

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: RecipesViewModel, onThemeChanged: () -> Unit, onRecipeClicked: (Int) -> Unit) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    viewModel.getRecipes()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recipeek".uppercase(),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Black)
                    )
                },
                backgroundColor = AppColorsTheme.colors.mainColor,
                actions = {
                    IconButton(
                        onClick = {
                            onThemeChanged()
                        }
                    ) {
                        Icon(Icons.Filled.Settings, null)
                    }
                }
            )
        },
        backgroundColor = AppColorsTheme.colors.uiBackground,
        contentColor = AppColorsTheme.colors.uiBackground,
        content = {
            // A surface container using the 'background' color from the theme
            Surface(color = AppColorsTheme.colors.uiBackground) {
                HomeScreenList(viewModel) { recipeId ->
                    onRecipeClicked(recipeId)
                }
            }
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomeScreenList(viewModel: RecipesViewModel, onNavigateTo: (petId: Int) -> Unit) {
    val recipes = viewModel.recipes.collectAsState().value

    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchInput(
            text = query,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            onTextChanged = {
                query = it
                viewModel.findRecipes(it)
            },
            hint = "Search for recipes"
        )

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(count = recipes.size) { position ->
                    HomeRecipeItem(
                        recipe = recipes[position],
                        onNavigateTo = onNavigateTo
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeRecipeItem(recipe: Recipe, onNavigateTo: (petId: Int) -> Unit) {
    var active by remember { mutableStateOf(false) }
    Button(
        onClick = { onNavigateTo(recipe.id) },
        shape = MaterialTheme.shapes.medium,
        elevation = null,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = if (active) AppColorsTheme.colors.mainColor.copy(alpha = ContentAlpha.disabled) else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
            .pointerMoveFilter(
                onEnter = {
                    active = true
                    false
                },
                onExit = {
                    active = false
                    false
                }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                load = { loadImageBitmap(recipe.imageUrl!!) },
                painterFor = { remember { BitmapPainter(it) } },
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.333f)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = recipe.title,
                style = if (active) MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Black) else MaterialTheme.typography.h4,
                color = AppColorsTheme.colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "${recipe.calories} cal • ${recipe.cookingTime} min",
                style = MaterialTheme.typography.caption,
                color = AppColorsTheme.colors.textHint,
            )
        }
    }
}
