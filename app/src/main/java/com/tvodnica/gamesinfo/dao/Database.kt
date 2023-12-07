package com.tvodnica.gamesinfo.dao

import com.tvodnica.gamesinfo.api.apimodels.GenreApi

interface Database {
    fun saveSelectedGenresIds(genres: MutableSet<GenreApi>)
    fun getSelectedGenresIds(): MutableSet<Int>
}