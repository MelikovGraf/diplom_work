package ru.netology.newprescription.activity

class GetRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipe(recipeId: Int): Recipe {
        return listOfRecipes.getRecipe(recipeId)
    }
}