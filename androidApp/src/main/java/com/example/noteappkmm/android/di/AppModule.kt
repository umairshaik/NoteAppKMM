package com.example.noteappkmm.android.di

import android.app.Application
import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSource
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.search.SearchNoteUseCase
import com.plcoding.noteappkmm.database.NoteDatabase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSQLDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun providesNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideSearchNoteUseCase(): SearchNoteUseCase {
        return SearchNoteUseCase()
    }

}