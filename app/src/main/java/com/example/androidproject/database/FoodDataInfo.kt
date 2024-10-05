package com.example.androidproject.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: String,
    val name: String,
    val hasMeat: Boolean,
    val hasNoodles: Boolean,
    val hasWater : Boolean,
    val hasVegetables: Boolean,
    val hasOthers: Boolean,
    val categoryId: String,
    val cookingMethodId: String,
    val cal: Int,
    val index: Int
): Parcelable
//@Parcelize
//data class ConfigDataCal(
//    var itemResult: MutableList<Item> = mutableListOf()
//) : Parcelable
//
//public fun getIndexITemList(index : Int){
//    val formatList = List<Item>(Meat(),)
//}

//menu,meat,noodles,water,vegetable,other
@Parcelize
open class Item(val id: String, val name: String, val cal: Int,val index:Int): Parcelable
{
    override fun toString(): String {
        return "Item(id='$id', name='$name', cal=$cal)"

    }
}
@Parcelize
class StringWrapper(var value: String): Parcelable

data class Meat(val meatId: String, val meatName: String, val meatCal: Int) : Item(meatId, meatName, meatCal,1)
data class Noodles(val noodlesId: String, val noodlesName: String, val noodlesCal: Int) : Item(noodlesId, noodlesName, noodlesCal,2)
data class Water(val waterId: String, val waterName: String, val waterCal: Int) : Item(waterId, waterName, waterCal,3)
data class Vegetable(val vegetableId: String, val vegetableName: String, val vegetableCal: Int) : Item(vegetableId, vegetableName, vegetableCal,4)
data class OtherIngredients(val ingredientId: String, val ingredientName: String, val ingredientCal: Int) : Item(ingredientId, ingredientName, ingredientCal,5)
data class CookingMethod(val methodId: String, val methodName: String, val methodCal: Int) : Item(methodId, methodName, methodCal,6)
data class Category(val categoryId: String, val categoryName: String, val categoryCal: Int) : Item(categoryId, categoryName, categoryCal,7)
