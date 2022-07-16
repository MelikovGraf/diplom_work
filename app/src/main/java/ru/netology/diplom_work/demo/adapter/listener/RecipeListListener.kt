package ru.netology.diplom_work.demo.adapter.listener

import ru.netology.diplom_work.activity.Recipe

interface RecipeListListener {

    fun onFavoriteClicked(recipe: Recipe)

    fun onRecipeClicked(recipe: Recipe)

    fun onSearchClicked(request: String)

    fun onAddClicked()

    fun onCancelClicked()

}