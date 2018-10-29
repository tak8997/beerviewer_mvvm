package home.self.beerviewer_mvvm.app_kotlin.network.calladapter

import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannelApi
import home.self.beerviewer_mvvm.app_kotlin.network.transformer.RetryingRxJava2Transformer
import io.reactivex.Single
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type
import javax.inject.Inject

internal class RetryingRxJava2CallAdapterFactory @Inject constructor(
        private val appChannel: AppChannelApi,
        private val delegate: RxJava2CallAdapterFactory,
        private val transformerFactory: RetryingRxJava2Transformer.Factory

): CallAdapter.Factory() {

    companion object {
        private const val MIN_IO_DIALOG_DISPLAY_MILLS = 100
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val defaultAdapter = delegate.get(returnType, annotations, retrofit)

        val rawType = CallAdapter.Factory.getRawType(returnType)

        return when(rawType) {
            Single::class.java -> RetryingRxJava2SingleCallAdapterFactory(appChannel, defaultAdapter as CallAdapter<Any, Single<Any>>, transformerFactory, MIN_IO_DIALOG_DISPLAY_MILLS)
            else -> defaultAdapter
        }
    }

}
