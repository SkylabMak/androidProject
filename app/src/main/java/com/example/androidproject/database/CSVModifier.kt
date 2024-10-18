package com.example.androidproject.database

import android.content.Context
import android.util.Log
import com.example.androidproject.SaveMenu
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CSVModifier(private val filename: String, private val context: Context) {

    fun isCsvFileExists(): Boolean {
        val file = File(context.filesDir, filename)
        Log.d("FileCheck", "CSV file path: ${file.absolutePath}")
        return file.exists()
    }

    fun copyCsvToInternalStorage(rawResourceId: Int) {
        val inputFile = context.resources.openRawResource(rawResourceId)
        val outputFile = context.openFileOutput(filename, Context.MODE_PRIVATE)
        inputFile.use { input ->
            outputFile.use { output ->
                input.copyTo(output)
                output.write("\n".toByteArray())
            }
        }
        val file = File(context.filesDir, filename)
        Log.d("FileCheck", "CSV file path: ${file.absolutePath}")
    }

    fun clearCSV() {
        val file = File(context.filesDir, filename)
        if (file.exists()) {
            file.delete() // Delete the file
        }
    }

    fun clearAndCopyCsvToInternalStorage(rawResourceId: Int) {
        clearCSV()
        copyCsvToInternalStorage(rawResourceId)
    }

    private fun saveMenuToList(menu: SaveMenu): List<String> {
        return listOf(
            menu.id,
            menu.name,
            menu.meat,
            menu.noodles,
            menu.water,
            menu.vegetables,
            menu.others,
            menu.categoryId,
            menu.cookingMethodId,
            menu.cal.toString(),
            menu.timestamp
        )
    }

    private fun listToSaveMenu(row: List<String>): SaveMenu {
        return SaveMenu(
            id = row[0],
            name = row[1],
            meat = row[2],
            noodles = row[3],
            water = row[4],
            vegetables = row[5],
            others = row[6],
            categoryId = row[7],
            cookingMethodId = row[8],
            cal = row[9].toInt(),
            timestamp = row[10]
        )
    }


    fun appendMenu(menu: SaveMenu) {
        val currentTimestamp = getCurrentTimestamp()
        val menuWithTimestamp = menu.copy(timestamp = currentTimestamp)
        val newRow = saveMenuToList(menuWithTimestamp)

        context.openFileOutput(filename, Context.MODE_APPEND).use { outputStream ->
            outputStream.bufferedWriter().use { writer ->
                writer.write(newRow.joinToString(","))
                writer.newLine()
            }
        }
    }

    fun readMenusFromCSV(skipHeader: Boolean = true): MutableList<SaveMenu> {
        val result = mutableListOf<SaveMenu>()

        context.openFileInput(filename).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                if (skipHeader) {
                    reader.readLine()
                }
                reader.forEachLine { line ->
                    val row = line.split(",")
                    result.add(listToSaveMenu(row))
                }
            }
        }
        return result
    }

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
