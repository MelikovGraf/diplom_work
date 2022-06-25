package ru.netology.newprescription.activity

data class Ingredient(
    val title: String,
    val value: String,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}