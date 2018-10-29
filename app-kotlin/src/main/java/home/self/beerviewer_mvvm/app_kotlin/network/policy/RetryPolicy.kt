package home.self.beerviewer_mvvm.app_kotlin.network.policy

import retrofit2.HttpException
import java.io.IOException


internal abstract class RetryPolicy: RetryPolicyApi {

    private val retryCodeList = listOf(404, 500)

    override fun shouldRetry(throwable: Throwable): Boolean {
        return when(throwable) {
            is IOException -> true
            is HttpException -> retryCodeList.contains(throwable.code())
            else -> false
        }
    }
}