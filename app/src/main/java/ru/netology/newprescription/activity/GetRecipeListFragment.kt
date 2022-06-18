package ru.netology.newprescription.activity

class GetRecipeListFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipeList(): List<RecipeItem> {
        return listOfRecipes.getRecipeList()
    }
}