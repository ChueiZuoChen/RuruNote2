package com.cz.rurunote2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Query("Select * from note ORDER BY id Desc")
    suspend fun getAllNote(): List<Note>

    @Insert
    suspend fun addMutipleNotes(vararg note: Note)

    @Query("Delete from note")
    suspend fun removeAllNotes()

    @Delete
    suspend fun removeNote(note: Note)
}
