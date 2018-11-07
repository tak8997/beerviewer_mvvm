package home.self.beerviewer_mvvm.app_kotlin.data.model


internal sealed class AppLooknFeel {
    sealed class Network: AppLooknFeel() {
        sealed class LoadingDialog : Network() {
            object Show : LoadingDialog()

            object Hide : LoadingDialog()
        }
    }
}