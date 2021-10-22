package data

data class Recipe(
    val id: Int,
    val title: String,
    val desc: String = "",
    val imageUrl: String? = null,
    val calories: Int = 0,
    val cookingTime: Int = 0, // In minutes
    val service: Int = 1,
    val ingredients: List<Ingredient> = listOf(),
    val steps: List<Step> = listOf(),
)

data class Step(
    val position: Int,
    val desc: String,
    val prepTime: Int? = null
)

data class Ingredient(
    val id: Int,
    val name: String,
    val desc: String? = null,
    val quantity: Int = 0,
    val quantityType: String? = null,
    val iconPath: String = "ic_ingredient.png"
)
