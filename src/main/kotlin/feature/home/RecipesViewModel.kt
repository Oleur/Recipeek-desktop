package feature.home

import data.Recipe
import data.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecipesViewModel constructor(
    private val repository: RecipeRepository
) {

    private val viewModelScope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = MutableStateFlow<Recipe?>(null)

    fun getRecipes() = viewModelScope.launch(Dispatchers.IO) {
        recipes.emit(repository.getRecipes())
    }

    fun findRecipes(query: String) = viewModelScope.launch(Dispatchers.IO) {
        if (query.isEmpty()) {
            recipes.emit(repository.getRecipes())
            return@launch
        }

        if (query.isNotEmpty() && query.length > 2) {
            recipes.emit(
                repository.getRecipes().filter { recipe ->
                    recipe.title.contains(query, true)
                }
            )
        }
    }

    fun getRecipe(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        recipe.emit(repository.getRecipe(id))
    }
}

