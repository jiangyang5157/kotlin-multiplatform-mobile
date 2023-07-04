import SwiftUI
import sharedSudoku

struct ContentView: View {
	let platform = MainKt.getPlatform()

	var body: some View {
		Text(platform)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}