package home.self.beerviewer_mvvm.app_kotlin.network.calladapter

import home.self.beerviewer_mvvm.app_kotlin.data.model.RemoteCallState
import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannelApi
import home.self.beerviewer_mvvm.app_kotlin.network.transformer.RetryingRxJava2Transformer
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


internal class RetryingRxJava2SingleCallAdapterFactory<T>(
        private val appChannel: AppChannelApi,
        private val delegate: CallAdapter<T, Single<T>>,
        private val transformerFactory: RetryingRxJava2Transformer.Factory,
        private val minIoDialogDisplayMills: Int

): CallAdapter<T, Single<T>> {

    override fun responseType(): Type = delegate.responseType()

    override fun adapt(call: Call<T>): Single<T> {
        val url = call.request().url().toString()

        appChannel.accept(RemoteCallState.Executed.OfInitial(url))

        val retryableCall = delegate.adapt(call).compose(transformerFactory.newInstance(url))

        val minDuration = Single.timer(minIoDialogDisplayMills.toLong(), TimeUnit.MILLISECONDS)

        return Singles
                .zip(retryableCall, minDuration) { response: T, _: Long ->
                    response
                }
                .map {
                    appChannel.accept(RemoteCallState.Succeed(url))

                    it
                }
    }

}