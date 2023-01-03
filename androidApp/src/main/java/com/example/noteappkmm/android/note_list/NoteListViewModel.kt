package com.example.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.search.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDatabase: NoteDataSource,
    private val savedStateHandle: SavedStateHandle,
    private val searchNoteUseCase: SearchNoteUseCase
) : ViewModel() {
    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(
        notes,
        searchText,
        isSearchActive
    ) { notes: List<Note>, searchText: String, isSearchActive: Boolean ->
        NoteListState(
            notes = searchNoteUseCase.execute(notes = notes, query = searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = NoteListState()
    )

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDatabase.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        isSearchActive.value.let {
            savedStateHandle["isSearchActive"] = !it
            if (!it) {
                savedStateHandle["searchText"] = ""
            }
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDatabase.deleteNoteById(id)
            loadNotes()
        }
    }
}