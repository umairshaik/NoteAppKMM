import shared
import SwiftUI

@main
struct iOSApp: App {
    private let databaseModule = DataBaseModule()

    var body: some Scene {
        WindowGroup {
            NavigationView(content: {
                NoteListScreen(noteDataSource: databaseModule.noteDatabase)
            }).accentColor(.black)
        }
    }
}
