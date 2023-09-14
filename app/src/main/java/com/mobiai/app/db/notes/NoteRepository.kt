package com.mobiai.app.db.notes


class NoteRepository {
    private val dataSource = NoteDataSource()
    suspend fun getListNote() = dataSource.getListNote()
    suspend fun getIdNote() = dataSource.getIdNote()
    fun addNote(note: Note) {
        dataSource.addNote(note)
    }
    fun updateNote(note: Note) {
        dataSource.updateNote(note)
    }
}