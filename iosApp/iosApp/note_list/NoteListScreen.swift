//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Shaik Umair Anjum on 10/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

struct NoteListScreen: View {
    private var noteDataSource: NoteDataSource
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)

    @State private var isNoteSelected = false
    @State private var selectedNoteId: Int64? = nil

    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    }

    var body: some View {
        VStack {
            ZStack {
                NavigationLink(
                    destination: NoteDetailsScreen(
                        noteDataSource: self.noteDataSource,
                        noteId: selectedNoteId),
                    isActive: $isNoteSelected) {
                        EmptyView()
                    }.hidden()

                HidableSearchTextField<NoteDetailsScreen>(
                    onSearchToggled: { viewModel.toggleIsSearchActive() },
                    destinationProvicer: {
                        NoteDetailsScreen(noteDataSource: self.noteDataSource, noteId: self.selectedNoteId)
                    },
                    isSearchActive: viewModel.searchActive,
                    searchText: $viewModel.searchText)
                    .frame(maxWidth: .infinity, minHeight: 40)
                    .padding()

                if !viewModel.searchActive {
                    Text("All Notes").font(.title2)
                }
            }

            List {
                ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                    Button(action: {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }) {
                        NoteItem(note: note, onDeleteClick: {
                            viewModel.deleteNoteById(id: note.id?.int64Value)
                        })
                    }
                }
            }.onAppear {
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)

        }.onAppear {
            viewModel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
}
