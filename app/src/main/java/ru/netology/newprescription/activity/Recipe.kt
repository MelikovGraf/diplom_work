package ru.netology.newprescription.activity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val title: String,
    val author: String,
    val authorId: Int,
    val cuisineCategory: String,
    val dishTime: String?,
    val ingredientsList: List<Ingredient>,
    val cookingList: List<CookingStage>,
    val previewURL: String? = null,
    val isIngredients: Boolean = false,
    val isCookingSteps: Boolean = false,
    val favorite: Boolean = false,
    val id: Int = IDENT
):Parcelable {

    companion object {
        const val IDENT = -1
    }
}
