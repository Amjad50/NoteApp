package com.amjad.opennote.data.entities

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "note_table")
open class Note(
    var type: NoteType,
    var title: String = "",
    var note: String = "",
    var date: Date? = null,
    var color: Int = Color.WHITE,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
) {
    fun getFormattedDate(): String {
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
        return date?.let { dateFormat.format(it) } ?: ""
    }

    open fun getNoteObject(): Note {
        return this
    }

    fun getCheckableListNote(): CheckableListNote {
        if (type != NoteType.CHECKABLE_LIST_NOTE)
            throw IllegalArgumentException("To get CheckableListNote note.type must be CHECKABLE_LIST_NOTE")

        return CheckableListNote(
            title,
            note,
            date,
            color,
            id
        )
    }

    /**
     * @return the subclassed object based on the value of type
     */
    fun getNoteBasedOnType(): Note {
        if (type == NoteType.CHECKABLE_LIST_NOTE)
            return getCheckableListNote()
        else
            return this
    }
}

