package home.self.beerviewer_mvvm.app_kotlin.network.transformer

import home.self.beerviewer_mvvm.app_kotlin.data.model.HttpUnsucessfulCause
import home.self.beerviewer_mvvm.app_kotlin.data.model.RemoteCallState
import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannelApi
import home.self.beerviewer_mvvm.app_kotlin.network.policy.RetryPolicyApi
import home.self.beerviewer_mvvm.app_kotlin.util.DelayCalculator
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


internal class HttpRetryPlan @Inject constructor(
        private val appChannel: AppChannelApi,
        private val retryPolicy: RetryPolicyApi,
        private val delayCalculator: DelayCalculator

): HttpRetryPlanApi {

    companion object {
        private const val MAX_RETRIES = 3
    }

    override fun apply(httpUnsuccessfulCause: HttpUnsucessfulCause): Boolean {
        val cause = httpUnsuccessfulCause.cause
        val retryCount = httpUnsuccessfulCause.retryCount

        val backoffDelay = delayCalculator.calculate(retryCount).value

        if(retryPolicy.shouldRetry(cause) && retryCount <= MAX_RETRIES) {
            appChannel.accept(RemoteCallState.RetryingBackoff(httpUnsuccessfulCause, backoffDelay))

            Thread.sleep(backoffDelay)

            appChannel.accept(RemoteCallState.Executed.OfRetry(httpUnsuccessfulCause))

            return true

        } else {
            when(cause) {
                is HttpException -> appChannel.accept(RemoteCallState.Failure.ByServer(httpUnsuccessfulCause))
                is IOException   -> appChannel.accept(RemoteCallState.Failure.ByNetwork(httpUnsuccessfulCause))
                else             -> appChannel.accept(RemoteCallState.Failure.ByNonTransient(httpUnsuccessfulCause))
            }

            return false
        }
    }

}