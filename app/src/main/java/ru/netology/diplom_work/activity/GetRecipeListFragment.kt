package ru.netology.diplom_work.activity

import androidx.lifecycle.LiveData
import ru.netology.diplom_work.activity.repository.RecipesOfList

class GetRecipeListFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipeList(): LiveData<List<Recipe>> {
        return listOfRecipes.getRecipeList()
    }
}