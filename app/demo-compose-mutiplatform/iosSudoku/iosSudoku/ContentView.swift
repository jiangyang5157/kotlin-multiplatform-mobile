import Foundation
import SwiftUI
import sharedSudoku

struct ComposeView: UIViewControllerRepresentable {
    func updaetUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
    func makeUIViewController(context: Context) -> some UIViewController {
        MainKt.MainViewController()
    }
}

struct ContentView: View {
	var body: some View {
		ComposeView()
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}