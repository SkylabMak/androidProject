package com.example.androidproject

import android.os.Parcelable
import com.example.androidproject.database.Item
import com.example.androidproject.database.Menu
import com.example.androidproject.database.StringWrapper
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfigDataCal
    (
    val name : StringWrapper,
    val menu: Menu,
    val item: List<Item>,//vegetables,meat,water,noodles,others
    val category: String,
    val method : String,
    val time : String,
    val saved : Boolean
) : Parcelable

@Parcelize
data class SaveMenu(
    val id: String,
    val name: String,
    val meat: String,
    val noodles: String,
    val water : String,
    val vegetables: String,
    val others: String,
    val categoryId: String,
    val cookingMethodId: String,
    val cal: Int,
    val timestamp: String
): Parcelable