package com.tvodnica.gamesinfo.helpers

import android.content.Context
import com.tvodnica.gamesinfo.api.apimodels.GenreApi
import java.io.File

const val SELECTED_GENRES_FILE_NAME = "selectedGenres"
const val DELIMITER = ";"
fun saveSelectedGenresToFile(context: Context, genres: MutableSet<GenreApi>) {
    var ids = ""
    genres.forEach { genre ->
        ids += genre.id
        ids += DELIMITER
    }
    File(context.filesDir, SELECTED_GENRES_FILE_NAME).writeText(ids)
}

fun getSelectedGenresIds(context: Context): MutableSet<Int> {
    val result = mutableSetOf<Int>()
    try {
        val allText = File(context.filesDir, SELECTED_GENRES_FILE_NAME).readText(Charsets.UTF_8)
        val values = allText.split(DELIMITER)

        values.forEach { value ->
            result.add(value.toInt())
        }
    } catch (_: Exception) {
    }
    return result
}