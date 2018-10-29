package home.self.beerviewer_mvvm.app_kotlin.data.model


internal data class HttpUnsucessfulCause(
    val url: String,
    val retryCount: Int,
    val cause: Throwable
)