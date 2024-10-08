import SwiftUI
import ComposeApp
import GoogleSignIn

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
      _ app: UIApplication,
      open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
      var handled: Bool

      handled = GIDSignIn.sharedInstance.handle(url)
      if handled {
        return true
      }

      // Handle other custom URL types.

      // If not handled by this app, return false.
      return false
    }


}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        InitKoinKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
            ContentView().onOpenURL { url in
                GIDSignIn.sharedInstance.handle(url)
            }
		}
	}
}
