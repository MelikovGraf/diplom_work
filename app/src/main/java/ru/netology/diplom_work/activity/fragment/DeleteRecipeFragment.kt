package ru.netology.diplom_work.activity.fragment

import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.activity.repository.RecipesOfList

class DeleteRecipeFragment(private val listOfRecipes: RecipesOfList) {

    fun deleteRecipe(recipe: Recipe) {
        listOfRecipes.deleteRecipe(recipe)
    }
}