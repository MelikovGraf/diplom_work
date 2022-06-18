package ru.netology.newprescription.activity

class EditRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun editRecipe(recipe: RecipeItem) {
        listOfRecipes.editRecipe(recipe)
    }
}