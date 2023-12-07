package com.tvodnica.gamesinfo.dao

import android.content.Context

class DatabaseFactory {
    companion object{
        fun getDatabase(context: Context) = FilesDatabase(context)
    }

}