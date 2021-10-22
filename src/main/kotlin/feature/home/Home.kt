package feature.home

import SearchInput
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import ui.common.atom.loadNetworkImage
import ui.theme.AppColorsTheme

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: RecipesViewModel, onRecipeClicked: (Int) -> Unit) {
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
                            // TODO
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
                load = { loadNetworkImage(recipe.imageUrl) },
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
