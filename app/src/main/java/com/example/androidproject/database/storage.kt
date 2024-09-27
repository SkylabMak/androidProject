package com.example.androidproject.database

import android.content.Context
import com.example.androidproject.R

class MenuStorage(context: Context) : MenuHandler(R.raw.menu, context)
class MeatCSVStorage(context: Context) : CSVHandlerItem<Meat>(R.raw.meat, context, { line -> Meat(line[0], line[1]) })
class VegetableCSVStorage(context: Context) : CSVHandlerItem<Vegetable>(R.raw.vegetable, context, { line -> Vegetable(line[0], line[1]) })
class NoodlesCSVHandler(context: Context) : CSVHandlerItem<Noodles>(R.raw.noodles, context, { line -> Noodles(line[0], line[1]) })
class OtherIngredientsCSVStorage(context: Context) : CSVHandlerItem<OtherIngredients>(R.raw.other_ingredients, context, { line -> OtherIngredients(line[0], line[1]) })
class CookingMethodCSVStorage(context: Context) : CSVHandlerItem<CookingMethod>(R.raw.cooking_method, context, { line -> CookingMethod(line[0], line[1]) })
class CategoryCSVStorage(context: Context) : CSVHandlerItem<Category>(R.raw.category, context, { line -> Category(line[0], line[1]) })
class WaterCSVStorage(context: Context) : CSVHandlerItem<Water>(R.raw.water, context, { line -> Water(line[0], line[1]) })
