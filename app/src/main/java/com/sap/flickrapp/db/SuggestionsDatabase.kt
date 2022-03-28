package com.sap.flickrapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

lateinit var db: SQLiteDatabase

/*Database Suggestions class from where it will insert, get suggestions to display on search*/
class SuggestionsDatabase(context: Context) {
    companion object {
        const val DB_SUGGESTION = "SUGGESTION_DB"
        const val TABLE_SUGGESTION = "SUGGESTION_TB"
        const val FIELD_ID = "_id"
        const val FIELD_SUGGESTION = "suggestion"
    }

    init {
        val helper = Helper(context, DB_SUGGESTION, null, 1)
        db = helper.writableDatabase
    }

    fun insertSuggestion(text: String): Long {
        val values = ContentValues()
        values.put(FIELD_SUGGESTION, text)
        return db.insertWithOnConflict(
            TABLE_SUGGESTION,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )

    }

    fun getSuggestions(text: String): Cursor {
        return db.query(
            TABLE_SUGGESTION, arrayOf<String>(FIELD_ID, FIELD_SUGGESTION),
            "$FIELD_SUGGESTION LIKE '$text%'", null, null, null, null
        )
    }

    /*inner class is defined to create the database table - Helper class*/
    inner class Helper(
        context: Context,
        name: String,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) :
        SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                ("CREATE TABLE " + TABLE_SUGGESTION + " (" +
                        FIELD_ID + " integer primary key autoincrement, " + FIELD_SUGGESTION + " text, UNIQUE(" + FIELD_SUGGESTION + ") ON CONFLICT REPLACE );")
            )
            Log.d("SUGGESTION", "DB CREATED")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }
}

