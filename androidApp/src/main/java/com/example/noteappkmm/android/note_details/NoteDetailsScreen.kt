package com.example.noteappkmm.android.note_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val hasNotBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()
    LaunchedEffect(key1 = hasNotBeenSaved) {
        if (hasNotBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.saveNote() }, backgroundColor = Color.Black) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add Note",
                tint = Color.White
            )
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChange = { viewModel.onNoteTitleChange(it) },
                onFocusChange = { focusState -> viewModel.onNoteTitleFocusChange(isFocused = focusState.isFocused) },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                modifier = Modifier.weight(1f),
                text = state.noteContent,
                hint = "Enter some content..",
                isHintVisible = state.isNoteContentHintVisible,
                onValueChange = viewModel::onNoteContentFocusChange,
                onFocusChange = { focusState -> viewModel.onNoteContentFocusChange(focusState.isFocused) },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }
    }
}