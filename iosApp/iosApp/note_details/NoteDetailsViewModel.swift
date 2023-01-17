//
//  NoteDetailsViewModel.swift
//  iosApp
//
//  Created by Shaik Umair Anjum on 17/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailsScreen {
    @MainActor class NoteDetailsViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource?
        private var noteId: Int64?

        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateColor()

        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }

        func loadNoteIfExists(id: Int64?) {
            if id != nil {
                noteId = id
                noteDataSource?.getNoteByID(id: id!, completionHandler: { note, _ in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().generateColor()
                })
            }
        }

        func saveNote(onSaved: @escaping () -> Void) {
            noteDataSource?.insertNote(
                note: Note(
                    id: noteId == nil ? nil : KotlinLong(value: noteId!),
                    title: noteTitle,
                    content: noteContent,
                    colorHex: noteColor,
                    created: DateTimeUtil().now()),
                completionHandler: { _ in
                    onSaved()
                })
        }

        func setParamsAndLoad(noteDataSource: NoteDataSource, noteId: Int64?) {
            self.noteDataSource = noteDataSource
            loadNoteIfExists(id: noteId)
        }
    }
}
