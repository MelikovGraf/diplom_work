package ru.netology.newprescription.activity

interface RecipesOfList {

    fun addRecipe(recipe: RecipeItem)

    fun deleteRecipe(recipe: RecipeItem)

    fun editRecipe(recipe: RecipeItem)

    fun getRecipe(recipeId: Int): RecipeItem

    fun getRecipeList(): List<RecipeItem>

}