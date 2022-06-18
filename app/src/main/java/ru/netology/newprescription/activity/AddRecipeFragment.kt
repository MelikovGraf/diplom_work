package ru.netology.newprescription.activity

class AddRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun addRecipe(recipe: RecipeItem) {
        listOfRecipes.addRecipe(recipe)
    }
}