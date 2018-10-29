package home.self.beerviewer_mvvm.app_kotlin.network.policy


internal interface RetryPolicyApi {
    fun shouldRetry(throwable: Throwable): Boolean
}