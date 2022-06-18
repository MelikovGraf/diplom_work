package ru.netology.newprescription.activity

class GetRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipe(recipeId: Int): RecipeItem {
        return listOfRecipes.getRecipe(recipeId)
    }
}