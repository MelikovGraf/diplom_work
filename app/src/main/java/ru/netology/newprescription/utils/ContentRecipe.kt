package ru.netology.newprescription.utils

import ru.netology.newprescription.activity.CuisineCategory

object ContentRecipe {

    fun getRandomCuisineCategory(): String {
        return CuisineCategory.cuisineCategory.random()
    }
}