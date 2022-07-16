package ru.netology.diplom_work.activity.fragment

import ru.netology.diplom_work.activity.Recipe
import ru.netology.diplom_work.activity.repository.RecipesOfList

class GetRecipeFragment(private val listOfRecipes: RecipesOfList) {

    fun getRecipe(recipeId: Int): Recipe {
        return listOfRecipes.getRecipe(recipeId)
    }
}