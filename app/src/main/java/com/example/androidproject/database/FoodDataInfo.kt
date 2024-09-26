package com.example.androidproject.database

data class Menu(
    val id: String,
    val name: String,
    val hasMeat: Boolean,
    val hasNoodles: Boolean,
    val hasVegetables: Boolean,
    val hasOthers: Boolean,
    val categoryId: String,
    val cookingMethodId: String
)

open class Item(val id: String, val name: String)

data class Meat(val meatId: String, val meatName: String) : Item(meatId, meatName)
data class Vegetable(val vegetableId: String, val vegetableName: String) : Item(vegetableId, vegetableName)
data class Noodles(val noodlesId: String, val noodlesName: String) : Item(noodlesId, noodlesName)
data class OtherIngredients(val ingredientId: String, val ingredientName: String) : Item(ingredientId, ingredientName)
data class CookingMethod(val methodId: String, val methodName: String) : Item(methodId, methodName)
data class Category(val categoryId: String, val categoryName: String) : Item(categoryId, categoryName)
data class Water(val waterId: String, val waterName: String) : Item(waterId, waterName)


