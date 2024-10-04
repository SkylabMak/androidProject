package com.example.androidproject.database

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
    val cal: Int
)

open class Item(val id: String, val name: String, val cal: Int)

data class Meat(val meatId: String, val meatName: String, val meatCal: Int) : Item(meatId, meatName, meatCal)
data class Vegetable(val vegetableId: String, val vegetableName: String, val vegetableCal: Int) : Item(vegetableId, vegetableName, vegetableCal)
data class Noodles(val noodlesId: String, val noodlesName: String, val noodlesCal: Int) : Item(noodlesId, noodlesName, noodlesCal)
data class OtherIngredients(val ingredientId: String, val ingredientName: String, val ingredientCal: Int) : Item(ingredientId, ingredientName, ingredientCal)
data class CookingMethod(val methodId: String, val methodName: String, val methodCal: Int) : Item(methodId, methodName, methodCal)
data class Category(val categoryId: String, val categoryName: String, val categoryCal: Int) : Item(categoryId, categoryName, categoryCal)
data class Water(val waterId: String, val waterName: String, val waterCal: Int) : Item(waterId, waterName, waterCal)
