package com.amjad.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): LiveData<List<Note>>


    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNote(id: Int): LiveData<Note>

    @Update
    fun updateNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNode(note: Note)
}