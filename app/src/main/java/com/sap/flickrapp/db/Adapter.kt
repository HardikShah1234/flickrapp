package com.sap.flickrapp.db

import android.content.Context
import android.database.Cursor
import android.widget.SimpleCursorAdapter

class Adapter(
    context: Context,
    layout: Int,
    c: Cursor,
    from: Array<String>,
    to: IntArray,
    flags: Int
) : SimpleCursorAdapter(context, layout, c, from, to, flags) {

    override fun convertToString(cursor: Cursor): CharSequence {
        val indexColumnSuggestion = cursor.getColumnIndex(SuggestionsDatabase.FIELD_SUGGESTION)
        return cursor.getString(indexColumnSuggestion)
    }
}