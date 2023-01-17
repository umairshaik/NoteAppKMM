package com.example.noteappkmm.android.note_details

data class NoteDetailsState(
    val noteTitle: String,
    val isNoteTitleHintVisible: Boolean,
    val noteContent: String,
    val isNoteContentHintVisible: Boolean,
    val noteColor: Long
)