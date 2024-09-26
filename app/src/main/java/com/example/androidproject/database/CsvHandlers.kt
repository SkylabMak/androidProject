package com.example.androidproject.database
import java.io.File
import kotlin.random.Random

abstract class CSVHandler(private val fileName: String) {
    protected fun readCSV(): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val file = File("database/$fileName")
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    result.add(line.split(","))
                }
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
        return if (allData.isNotEmpty()) allData[Random.nextInt(allData.size)] else null
    }
}

abstract class MenuHandler(fileName: String) : CSVHandler(fileName) {
    private val menus: List<Menu> = readCSV().map { line ->
        Menu(
            id = line[0],
            name = line[1],
            hasMeat = line[2] == "1",
            hasNoodles = line[3] == "1",
            hasVegetables = line[4] == "1",
            hasOthers = line[5] == "1",
            categoryId = line[6],
            cookingMethodId = line[7]
        )
    }

    fun findAll(): List<Menu> {
        return menus
    }

    fun getById(id: String): Menu? {
        return menus.firstOrNull { it.id == id }
    }

    fun randomManu(): Menu? {
        return if (menus.isNotEmpty()) menus.random() else null
    }
}

abstract class CSVHandlerItem<T : Item>(private val fileName: String, private val mapper: (List<String>) -> T) {

    private val items: List<T> = readCSV().map { line -> mapper(line) }

    private fun readCSV(): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val file = File("database/$fileName")
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    result.add(line.split(","))
                }
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
}

