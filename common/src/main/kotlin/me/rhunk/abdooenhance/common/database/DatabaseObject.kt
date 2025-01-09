package me.rhunk.abdooenhance.common.database

import android.database.Cursor

interface DatabaseObject {
    fun write(cursor: Cursor)
}
