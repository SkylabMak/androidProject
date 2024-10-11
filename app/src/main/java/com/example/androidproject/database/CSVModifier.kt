package com.example.androidproject.database

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CSVModifier(private val filename: String, private val context: Context) {

    fun isCsvFileExists(): Boolean {
        val file = File(context.filesDir, filename)
        return file.exists()
    }

    fun copyCsvToInternalStorage(rawResourceId: Int) {
        val inputStream = context.resources.openRawResource(rawResourceId)
        val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
                output.write("\n".toByteArray())
            }
        }
    }

    // Clear (delete) the existing CSV file from internal storage
    fun clearCSV() {
        val file = File(context.filesDir, filename)
        if (file.exists()) {
            file.delete() // Delete the file
        }
    }

    // Clear and re-copy CSV from resources
    fun clearAndCopyCsvToInternalStorage(rawResourceId: Int) {
        clearCSV() // Clear the existing file
        copyCsvToInternalStorage(rawResourceId) // Copy again from resources
    }

    // Convert SaveMenu to List<String> for CSV row
    private fun saveMenuToRow(menu: SaveMenu): List<String> {
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

    // Convert List<String> back to SaveMenu object
    private fun rowToSaveMenu(row: List<String>): SaveMenu {
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


    // Write a list of SaveMenu objects to the CSV
    fun writeCSV(data: List<SaveMenu>) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.bufferedWriter().use { writer ->
                data.forEach { menu ->
                    writer.write(saveMenuToRow(menu).joinToString(","))
                    writer.newLine()
                }
            }
        }
    }

    // Add a new SaveMenu and rewrite the CSV
    fun addMenu(menu: SaveMenu) {
        val currentData = readMenusFromCSV()
        val menuWithTimestamp = menu.copy(timestamp = getCurrentTimestamp())
        currentData.add(menuWithTimestamp)
        writeCSV(currentData)
    }

    // Append SaveMenu as a new row
    fun appendMenu(menu: SaveMenu) {
        val currentTimestamp = getCurrentTimestamp()
        val menuWithTimestamp = menu.copy(timestamp = currentTimestamp)
        val newRow = saveMenuToRow(menuWithTimestamp)

        context.openFileOutput(filename, Context.MODE_APPEND).use { outputStream ->
            outputStream.bufferedWriter().use { writer ->
                writer.write(newRow.joinToString(","))
                writer.newLine()  // Move to the next line after writing the row
            }
        }
    }

    // Read the CSV and return a list of SaveMenu objects, skipping the header
    fun readMenusFromCSV(skipHeader: Boolean = true): MutableList<SaveMenu> {
        val result = mutableListOf<SaveMenu>()

        context.openFileInput(filename).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                if (skipHeader) {
                    reader.readLine() // Skip the first line (header)
                }
                reader.forEachLine { line ->
                    val row = line.split(",")
                    result.add(rowToSaveMenu(row))
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
