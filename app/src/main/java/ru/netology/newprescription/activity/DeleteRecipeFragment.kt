package ru.netology.newprescription.activity

class DeleteRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun deleteRecipe(recipe: Recipe) {
        listOfRecipes.deleteRecipe(recipe)
    }
}