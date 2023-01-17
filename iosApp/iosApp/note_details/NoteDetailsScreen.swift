//
//  NoteDetailsScreen.swift
//  iosApp
//
//  Created by Shaik Umair Anjum on 17/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

struct NoteDetailsScreen: View {
    private var noteDataSource: NoteDataSource
    private var noteId: Int64?

    @StateObject var viewModel = NoteDetailsViewModel(noteDataSource: nil)

    @Environment(\.presentationMode) var presentation

    init(noteDataSource: NoteDataSource, noteId: Int64? = nil) {
        self.noteDataSource = noteDataSource
        self.noteId = noteId
    }

    var body: some View {
        VStack {
            TextField(
                "Enter a title..",
                text: $viewModel.noteTitle).font(.title)
            TextField(
                "Enter some content...",
                text: $viewModel.noteContent)
            Spacer()
        }.toolbar(content: {
            Button(
                action: { viewModel.saveNote {
                    self.presentation.wrappedValue.dismiss()
                }},
                label: { Image(systemName: "checkmark") })
        })
        .padding()
        .background(Color(hex: viewModel.noteColor))
        .onAppear {
            viewModel.setParamsAndLoad(
                noteDataSource: self.noteDataSource, noteId: self.noteId)
        }
    }
}

struct NoteDetailsScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
