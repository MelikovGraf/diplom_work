package ru.netology.newprescription.activity

class EditRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun editRecipe(recipe: Recipe) {
        listOfRecipes.editRecipe(recipe)
    }
}