package feature.recipe

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import data.Ingredient
import data.Recipe
import data.Step
import feature.home.RecipesViewModel
import ui.common.atom.AsyncImage
import ui.common.atom.loadNetworkImage
import ui.theme.AppColorsTheme
import ui.theme.RecipeekShapes

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun RecipeDetailsScreen(recipeId: Int? = null, viewModel: RecipesViewModel, onClose: () -> Unit = {}) {
    recipeId?.let {
        viewModel.getRecipe(recipeId)
    }

    val recipe = viewModel.recipe.collectAsState().value
    checkNotNull(recipe) { return }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(scrollState)
    ) {
        Box {
            AsyncImage(
                load = { loadNetworkImage(recipe.imageUrl) },
                painterFor = { remember { BitmapPainter(it) } },
                contentScale = ContentScale.Crop,
                contentDescription = "Recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.777f)
                    .padding(bottom = 32.dp)
            )

            RecipeCard(
                recipe = recipe,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        if (recipe.ingredients.isNotEmpty()) {
            IngredientList(
                ingredients = recipe.ingredients,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
            )
        }

        if (recipe.steps.isNotEmpty()) {
            CookingSteps(
                steps = recipe.steps,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp),
        shape = RecipeekShapes.medium,
        backgroundColor = AppColorsTheme.colors.uiBackground,
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 16.dp),
        ) {
            Text(
                text = recipe.title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color = AppColorsTheme.colors.text
            )
            Text(
                text = "${recipe.ingredients.size} ingrédients",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.caption,
                color = AppColorsTheme.colors.textHint
            )
        }
    }
}

@Composable
fun IngredientList(ingredients: List<Ingredient>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Ingrédients",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.h5,
            color = AppColorsTheme.colors.text,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            items(ingredients.size) { position ->
                IngredientItem(ingredient = ingredients[position])
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Column {
        Image(
            painter = painterResource(ingredient.iconPath),
            contentDescription = ingredient.name,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Color.Red.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(8.dp)
        )
        Text(
            text = ingredient.name,
            modifier = Modifier.align(Alignment.Start).width(80.dp),
            style = MaterialTheme.typography.h6,
            color = AppColorsTheme.colors.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${ingredient.quantity} ${ingredient.quantityType}",
            modifier = Modifier.align(Alignment.Start).width(80.dp),
            style = MaterialTheme.typography.body2,
            color = AppColorsTheme.colors.textHint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@ExperimentalAnimationApi
@Composable
fun CookingSteps(steps: List<Step>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Préparation",
            style = MaterialTheme.typography.h5,
            color = AppColorsTheme.colors.text,
        )
        Spacer(modifier = Modifier.size(8.dp))
        steps.forEach { step ->
            StepItem(step = step)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun StepItem(step: Step) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(
                color = Color(0x3300b388),
                shape = RecipeekShapes.medium.copy(all = CornerSize(32.dp))
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Etape ${step.position}",
            style = MaterialTheme.typography.h6,
            color = AppColorsTheme.colors.mainColor
        )
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                fadeOut(animationSpec = tween(150)) using
                SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        keyframes {
                            // Expand horizontally first.
                            IntSize(targetSize.width, initialSize.height) at 150
                            durationMillis = 300
                        }
                    } else {
                        keyframes {
                            // Shrink vertically first.
                            IntSize(initialSize.width, targetSize.height) at 150
                            durationMillis = 300
                        }
                    }
                }
            }
        ) { targetExpanded ->
            Text(
                text = step.desc,
                modifier = Modifier.clickable { expanded = !expanded },
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium),
                color = AppColorsTheme.colors.text,
                maxLines = if (targetExpanded) Integer.MAX_VALUE else 3,
                overflow = if (targetExpanded) TextOverflow.Clip else TextOverflow.Ellipsis
            )
        }
    }
}
