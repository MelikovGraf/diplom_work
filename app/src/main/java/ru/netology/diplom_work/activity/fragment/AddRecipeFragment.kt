package ru.netology.diplom_work.activity.fragment

import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.activity.repository.RecipesOfList

class AddRecipeFragment(private val listOfRecipes: RecipesOfList) {

    fun addRecipe(recipe: Recipe) {
        listOfRecipes.addRecipe(recipe)
    }
}