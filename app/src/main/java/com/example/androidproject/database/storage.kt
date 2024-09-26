package com.example.androidproject.database



class ManuStorage : MenuHandler("Menu.csv")
class MeatCSVStorage : CSVHandlerItem<Meat>("meat.csv", { line -> Meat(line[0], line[1]) })
class VegetableCSVStorage : CSVHandlerItem<Vegetable>("vegetable.csv", { line -> Vegetable(line[0], line[1]) })
class NoodlesCSVHandler : CSVHandlerItem<Noodles>("noodles.csv", { line -> Noodles(line[0], line[1]) })
class OtherIngredientsCSVStorage : CSVHandlerItem<OtherIngredients>("otherIngredients.csv", { line -> OtherIngredients(line[0], line[1]) })
class CookingMethodCSVStorage : CSVHandlerItem<CookingMethod>("cookingMethod.csv", { line -> CookingMethod(line[0], line[1]) })
class CategoryCSVStorage : CSVHandlerItem<Category>("category.csv", { line -> Category(line[0], line[1]) })
class WaterCSVStorage : CSVHandlerItem<Water>("water.csv", { line -> Water(line[0], line[1]) })