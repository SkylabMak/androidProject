package com.example.androidproject.database

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Integer.parseInt

abstract class CSVHandler(private val rawResourceId: Int, private val context: Context) {
    protected fun readCSV(): List<List<String>> {
        val result = mutableListOf<List<String>>()

        val inputStream = context.resources.openRawResource(rawResourceId)
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readLine() // Skip the first line (header)
            reader.forEachLine { line ->
                result.add(line.split(","))
            }
        }
        return result
    }

    fun findById(id: String): List<String>? {
        return readCSV().firstOrNull { it[0] == id }
    }

    fun getAll(): List<List<String>> {
        return readCSV()
    }

    fun random(): List<String>? {
        val allData = readCSV()
        return if (allData.isNotEmpty()) allData.random() else null
    }
}

abstract class CSVHandlerItem<T : Item>(private val rawResourceId: Int, private val context: Context, private val mapper: (List<String>) -> T) {

    private val items: List<T> = readCSV().map { line -> mapper(line) }

    private fun readCSV(): List<List<String>> {
        val result = mutableListOf<List<String>>()

        // อ่านไฟล์จาก res/raw
        val inputStream = context.resources.openRawResource(rawResourceId)
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readLine() // Skip the first line (header)
            reader.forEachLine { line ->
                result.add(line.split(","))
            }
        }
        return result
    }

    fun findAll(): List<T> {
        return items
    }

    fun findById(id: String): T? {
        return items.firstOrNull { it.id == id }
    }

    fun findRandom(): T? {
        return if (items.isNotEmpty()) items.random() else null
    }

    fun findRandom(id:String): T? {
        val filtered = items.filter { it.id!=id}
        return if (filtered.isNotEmpty()) filtered.random() else null
    }
}

open class  MenuHandler(rawResourceId: Int, context: Context) : CSVHandler(rawResourceId, context) {
    private val menus: List<Menu> = readCSV().map { line ->
        Menu(
            id = line[0],
            name = line[1],
            hasMeat = line[2] == "1",
            hasNoodles = line[3] == "1",
            hasWater = line[4] == "1",
            hasVegetables = line[5] == "1",
            hasOthers = line[6] == "1",
            categoryId = line[7],
            cookingMethodId = line[8],
            cal = parseInt(line[9])
        )
    }

    fun findAll(): List<Menu> {
        return menus
    }

    fun getById(id: String): Menu? {
        return menus.firstOrNull { it.id == id }
    }

    fun randomMenu(category: String, cookingMethod: String, vegetable: String, meat: String): Menu? {
        return if (category == "0" && cookingMethod == "0") {
            // คืนค่าสุ่มเมนูจากรายการทั้งหมดถ้าไม่กรองหมวดหมู่และวิธีทำอาหาร
            if (menus.isNotEmpty()) menus.random() else null
        } else {
            // กรองเมนูตามหมวดหมู่และวิธีทำอาหาร
            val filteredMenus = filterMenus(category, cookingMethod)
            // ตรวจสอบว่ารายการที่กรองมาได้ว่างหรือไม่
            return if (filteredMenus.isNotEmpty()) {
                val randomEd = filteredMenus.random()
                Log.d("filteredMenus", "$filteredMenus")
                Log.d("randomMenu", "$randomEd")
                randomEd
            } else {
                Log.d("filteredMenus", "No menus available after filtering")
                null
            }
        }
    }


    public fun filterMenus(category: String? = null, cookingMethod: String? = null): List<Menu> {
        Log.d("filterID", "$category $cookingMethod")
        return menus.filter { menu ->
            val isCategoryMatch = category?.let { it == menu.categoryId } ?: true
            val isCookingMethodMatch = cookingMethod?.let { it == menu.cookingMethodId } ?: true
            isCategoryMatch && isCookingMethodMatch
        }
    }

}
