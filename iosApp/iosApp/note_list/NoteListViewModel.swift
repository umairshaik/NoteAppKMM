//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Shaik Umair Anjum on 10/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen{
    @MainActor class NoteListViewModel: ObservableObject{
        private var noteDataSource: NoteDataSource? = nil
        
        private let searchNote = SearchNoteUseCase()
        
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = ""{
            didSet{
                self.filteredNotes = searchNote.execute(notes: self.notes, query: searchText)
            }
        }
        
        @Published private(set) var searchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes(){
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = notes ?? []
            })
        }
        
        func deleteNoteById(id: Int64?)  {
            if id != nil {
                noteDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive()  {
            self.searchActive = !self.searchActive
            if !searchActive{
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource: NoteDataSource) {
            self.noteDataSource = noteDataSource
        }
    }
}
