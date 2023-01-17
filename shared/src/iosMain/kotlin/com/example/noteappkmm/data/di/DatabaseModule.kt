package com.example.noteappkmm.data.di

import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSource
import com.example.noteappkmm.domain.note.NoteDataSource
import com.plcoding.noteappkmm.database.NoteDatabase

class DataBaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDatabase: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}