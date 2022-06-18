package ru.netology.newprescription.activity

import androidx.lifecycle.LiveData

class GetRecipeListFragment(
    private val listOfRecipes: RecipesOfList
) {

    fun getRecipeList(): LiveData<List<Recipe>> {
        return listOfRecipes.getRecipeList()
    }
}