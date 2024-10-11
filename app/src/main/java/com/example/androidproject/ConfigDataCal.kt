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