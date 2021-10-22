package data

class RecipeRepository() {

    fun getRecipes() = recipes

    fun getRecipe(id: Int) = recipes.find { it.id == id }

    companion object {
        private val recipes = listOf(
            Recipe(
                id = 0,
                title = "Ballotine de dinde automnale",
                desc = "Une ballotine de poulet farcie à la pancetta poivrée, coulis de poivrons rouges et Ossau-Iraty.\n" +
                    "Sur une mousseline de pomme de terre et chanterelles roties au beurre",
                imageUrl = "https://images.unsplash.com/photo-1633796331241-4bb7e4658d56?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 876,
                cookingTime = 90,
                ingredients = listOf(
                    Ingredient(
                        id = 0,
                        name = "Chicken",
                        quantity = 2,
                        quantityType = "tranches",
                    ),
                    Ingredient(
                        id = 1,
                        name = "Pancetta",
                        quantity = 4,
                        quantityType = "tranches",
                    ),
                    Ingredient(
                        id = 2,
                        name = "Poivronade",
                        quantity = 10,
                        quantityType = "cl",
                    ),
                    Ingredient(
                        id = 3,
                        name = "Pomme de terre",
                        quantity = 300,
                        quantityType = "g",
                    ),
                    Ingredient(
                        id = 4,
                        name = "Chanterelles",
                        quantity = 150,
                        quantityType = "g",
                    ),
                ),
                steps = listOf(
                    Step(
                        position = 1,
                        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    ),
                    Step(
                        position = 2,
                        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    ),
                    Step(
                        position = 3,
                        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    ),
                    Step(
                        position = 4,
                        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    ),
                    Step(
                        position = 5,
                        desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    ),
                ),
            ),
            Recipe(
                id = 1,
                title = "Le boeuf bourguignon",
                imageUrl = "https://images.unsplash.com/photo-1633799710991-e8a8cebea471?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 1574,
                cookingTime = 270,
            ),
            Recipe(
                id = 2,
                title = "Risotto d'asperges sauvages",
                imageUrl = "https://images.unsplash.com/photo-1633800197170-6af1eecb2319?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 1200,
                cookingTime = 45,
            ),
            Recipe(
                id = 3,
                title = "Pancakes et fraises",
                imageUrl = "https://images.unsplash.com/photo-1633800121138-f98d81e85ff9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
            ),
            Recipe(
                id = 4,
                title = "Les Nuggets",
                imageUrl = "https://images.unsplash.com/photo-1633799897655-325beb35113c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
            ),
        )
    }
}
