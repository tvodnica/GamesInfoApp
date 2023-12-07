package com.tvodnica.gamesinfo.dao

import android.content.Context
import android.util.Log
import com.tvodnica.gamesinfo.api.apimodels.GenreApi
import java.io.File

private const val SELECTED_GENRES_FILE_NAME = "selectedGenres"
private const val DELIMITER = ";"

class FilesDatabase(
    val context: Context
) : Database {

    override fun saveSelectedGenresIds(genres: MutableSet<GenreApi>) {
        var ids = ""
        genres.forEach { genre ->
            ids += genre.id
            ids += DELIMITER
        }
        File(context.filesDir, SELECTED_GENRES_FILE_NAME).writeText(ids)
    }

    override fun getSelectedGenresIds(): MutableSet<Int> {
        val result = mutableSetOf<Int>()
        try {
            val allText = File(context.filesDir, SELECTED_GENRES_FILE_NAME).readText(Charsets.UTF_8)
            val values = allText.split(DELIMITER)

            values.forEach { value ->
                result.add(value.toInt())
            }
        } catch (e: Exception) {
            Log.e("Tag", "Error at reading selected genre ids from file: " + e.message)
        }
        return result
    }
}