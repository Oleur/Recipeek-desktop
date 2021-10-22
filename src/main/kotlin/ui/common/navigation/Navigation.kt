package ui.common.navigation

sealed class Nav
object Home : Nav()
data class RecipeDetails(val recipeId: Int) : Nav()