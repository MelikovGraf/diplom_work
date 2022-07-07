package ru.netology.newprescription.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.newprescription.activity.CookingStage
import ru.netology.newprescription.activity.Ingredient
import ru.netology.newprescription.activity.Recipe
import ru.netology.newprescription.activity.repository.RecipesOfList
import ru.netology.newprescription.utils.ContentRecipe
import kotlin.random.Random

object RecipeRepositoryImpl : RecipesOfList { // Список рецептов

    private const val RECIPE_COUNT = 10
    private const val INGREDIENTS_COUNT = 5
    private const val COOKING_COUNT = 5

    private var id = 0
    private val recipeListData = MutableLiveData<List<Recipe>>()
    private val recipeList = mutableListOf<Recipe>()
    private var orderedRecipeList = mutableListOf<Recipe>()


    init {
        for (r in 0..RECIPE_COUNT) {
            val newRecipe = Recipe(
                title = "Recipe №$r",
                author = "Favourites",
                authorId = Random.nextInt(1, 5),
                cuisineCategory = ContentRecipe.getRandomCuisineCategory(),
                dishTime = "0h\n50min",
                ingredientsList =
                List(INGREDIENTS_COUNT) {
                    Ingredient(
                        "Ingredient $it",
                        "${Random.nextInt(10, 100)} шт.",
                        it
                    )
                },
                cookingList =
                List(COOKING_COUNT) {
                    CookingStage(
                        descript = "Description $it",
                        stageImageUri = ContentRecipe.setRandomCookingStepImage(),
                        id = it
                    )
                },
                previewUri = ContentRecipe.setRandomImagePreview()
            )
            addRecipe(newRecipe)
        }
    }

    override fun addRecipe(recipe: Recipe) {
        if (recipe.id == Recipe.IDENT) {
            val recipeId = id++
            recipeList.add(recipe.copy(id = recipeId))
        } else editRecipe(recipe)
        updateList(isSorted = false)
    }

    override fun deleteRecipe(recipe: Recipe) {
        recipeList.remove(recipe)
        updateList(isSorted = false)
    }

    override fun editRecipe(recipe: Recipe) {
        recipeList.replaceAll {
            if (it.id == recipe.id) recipe else it
        }
        updateList(isSorted = false)
    }

    override fun getRecipe(recipeId: Int): Recipe {
        return recipeList.find {
            it.id == recipeId
        } ?: throw RuntimeException("The recipe's $recipeId is not in the list!")
    }

    override fun getRecipeList(): LiveData<List<Recipe>> {
        return recipeListData
    }

    override fun isFavorite(recipeId: Int) {
        recipeList.replaceAll { if (it.id == recipeId) it.copy(favorite = !it.favorite) else it }
        if (orderedRecipeList.isEmpty()) {
            updateList(isSorted = false)
        } else {
            orderedRecipeList.replaceAll { if (it.id == recipeId) it.copy(favorite = !it.favorite) else it }
            updateList(isSorted = true)
        }
    }

    override fun searchRecipe(request: String) {
        if (request == RecipesOfList.CANCEL_SEARCH) {
            orderedRecipeList.clear()
            updateList(isSorted = false)
        } else {
            orderedRecipeList = recipeList.filter {
                it.title.contains(request, ignoreCase = true) || it.author.contains(
                    request,
                    ignoreCase = true
                )
            } as MutableList<Recipe>
            updateList(isSorted = true)
        }
    }

    private fun updateList(isSorted: Boolean) {
        if (isSorted)
            recipeListData.value = orderedRecipeList.toList()
        else
            recipeListData.value = recipeList.toList()
    }
}