package home.self.beerviewer_mvvm.app_kotlin.network.transformer

import home.self.beerviewer_mvvm.app_kotlin.data.model.HttpUnsucessfulCause
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import javax.inject.Inject


internal class RetryingRxJava2Transformer<T> @Inject constructor(
        private val url: String,
        private val httpRetryPlan: HttpRetryPlanApi

): SingleTransformer<T, T> {

    interface Factory {
        fun <T> newInstance(url: String): RetryingRxJava2Transformer<T>
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.retry { retryCount: Int, cause: Throwable ->
            return@retry httpRetryPlan.apply(HttpUnsucessfulCause(url, retryCount, cause))
        }
    }

}