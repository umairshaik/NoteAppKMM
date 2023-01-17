//
//  HidableSearchTextField.swift
//  iosApp
//
//  Created by Shaik Umair Anjum on 14/01/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HidableSearchTextField<Destination: View>: View {
    
    var onSearchToggled: () -> Void
    var destinationProvicer: () -> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack{
            TextField("Search ...", text:$searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            
            if !isSearchActive{
                Spacer()
            }
            
            Button(action: onSearchToggled){
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
            }.foregroundColor(.black)
            
            NavigationLink(destination: destinationProvicer, label: {Image(systemName: "plus")})
                .foregroundColor(.black)
        }
    }
}

struct HidableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HidableSearchTextField(
            onSearchToggled: { },
            destinationProvicer: { EmptyView() },
            isSearchActive: true,
            searchText: .constant("Hello World"))
    }
}
