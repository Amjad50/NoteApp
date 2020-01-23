package com.amjad.opennote.repositories

import androidx.lifecycle.LiveData
import com.amjad.opennote.data.Note
import com.amjad.opennote.data.NoteDao

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNote(id: Long): LiveData<Note> {
        return noteDao.getNote(id)
    }

    suspend fun insert(note: Note): Long {
        return noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNotes(notesIds: List<Long>) {
        noteDao.deleteNotes(notesIds)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun updateNotesColor(notesIds: List<Long>, color: Int) {
        noteDao.updateNotesColor(notesIds, color)
    }
}