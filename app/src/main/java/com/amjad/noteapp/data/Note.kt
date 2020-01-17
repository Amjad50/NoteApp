package com.amjad.noteapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    var title: String = "",
    var note: String = "",
    var date: Date = Date(),
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
) {
    fun getFormattedDate(): String {
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
        return date.let { dateFormat.format(it) } ?: ""
    }
}

