package com.example.noteappkmm.domain.search

import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.time.DateTimeUtil

class SearchNoteUseCase {

    fun execute(notes: List<Note>, query: String): List<Note> {
        return if (query.isBlank()) {
            notes
        } else {
            notes.filter { it.content.contains(query) || it.title.contains(query) }
                .sortedBy { DateTimeUtil.toEpochMillis(it.created) }
        }
    }
}