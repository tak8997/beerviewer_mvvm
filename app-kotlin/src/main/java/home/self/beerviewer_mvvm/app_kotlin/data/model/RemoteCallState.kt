package home.self.beerviewer_mvvm.app_kotlin.data.model

internal sealed class RemoteCallState {
    
    sealed class Executed: RemoteCallState() {

        data class OfInitial(val url: String) : Executed()

        data class OfRetry(val httpUnsuccessfulCause: HttpUnsucessfulCause) : Executed()
    }

    data class Succeed(val url: String) : RemoteCallState()

    data class RetryingBackoff(val httpUnsuccessfulCause: HttpUnsucessfulCause, val backoffDelay: Long) : RemoteCallState()

    sealed class Failure : RemoteCallState() {
        data class ByNonTransient(val httpUnsuccessfulCause: HttpUnsucessfulCause) : Failure()

        data class ByNetwork(val httpUnsuccessfulCause: HttpUnsucessfulCause) : Failure()

        data class ByServer(val httpUnsuccessfulCause: HttpUnsucessfulCause) : Failure()
    }

}
