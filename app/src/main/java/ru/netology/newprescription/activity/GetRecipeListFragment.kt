package ru.netology.newprescription.activity

import androidx.lifecycle.LiveData
import ru.netology.newprescription.activity.repository.RecipesOfList

class GetRecipeListFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipeList(): LiveData<List<Recipe>> {
        return listOfRecipes.getRecipeList()
    }
}