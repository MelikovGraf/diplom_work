package ru.netology.diplom_work.activity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CookingStage(
    val descript: String,
    val uri: String? = null,
    val stageImageUri: Uri? = null,
    val id: Int = UNDEFINED_ID

):Parcelable {

    companion object {
        const val UNDEFINED_ID = -1
    }
}