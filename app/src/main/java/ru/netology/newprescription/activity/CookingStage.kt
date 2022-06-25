package ru.netology.newprescription.activity

data class CookingStage(
    val descript: String,
    val id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}