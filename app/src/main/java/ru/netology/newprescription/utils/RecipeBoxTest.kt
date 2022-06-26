package ru.netology.newprescription.utils

import android.content.Context
import android.widget.Toast
import ru.netology.newprescription.activity.Recipe

object RecipeBoxTest {

    fun testRecipeEmptyBox(context: Context, recipe: Recipe): Boolean {
        return when {
            recipe.title.isBlank() -> {
                toastMaker(context, "The title should not be missing")
                false
            }
            recipe.ingredientsList.isEmpty() -> {
                toastMaker(context, "Add the ingredient")
                false
            }
            recipe.cookingList.isEmpty() -> {
                toastMaker(context, "add the cooking instructions stage")
                false
            }
            else -> true
        }
    }

    private fun toastMaker(context: Context, value: String) {
        Toast.makeText(
            context,
            value,
            Toast.LENGTH_SHORT
        ).show()
    }
}