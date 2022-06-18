package ru.netology.newprescription.activity

class DeleteRecipeFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun deleteRecipe(recipe: RecipeItem) {
        listOfRecipes.deleteRecipe(recipe)
    }
}