package ru.netology.newprescription.activity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CookingStage(
    val descript: String,
    val uri: String? = null,
    val id: Int = UNDEFINED_ID

):Parcelable {

    companion object {
        const val UNDEFINED_ID = -1
    }
}