package home.self.beerviewer_mvvm.app_kotlin.network

import home.self.beerviewer_mvvm.app_kotlin.data.model.AppLooknFeel
import home.self.beerviewer_mvvm.app_kotlin.data.model.RemoteCallState
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannelApi
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


internal class HttpInteractor @Inject constructor(
        private val appChannel: AppChannelApi,
        private val disposables: CompositeDisposable,
        private val scheduler: BaseSchedulerProvider

): HttpInteractorApi {

    companion object {
        private const val MAX_RETIRES = 3
    }

    override fun start() {
        disposables.addAll(
                appChannel.ofRemoteCallState()
                        .observeOn(scheduler.io())
                        .subscribeBy(
                                onNext = {
                                    when(it) {
                                        is RemoteCallState.Executed.OfInitial -> {
                                            appChannel.accept(AppLooknFeel.Network.LoadingDialog.Show)
                                        }

                                        is RemoteCallState.Executed.OfRetry -> {
                                            if(it.httpUnsuccessfulCause.retryCount == MAX_RETIRES) {
//                                                appChannel.accept(AppLooknFeel.Network.)
                                            }
                                        }
                                    }
                                }
                        )
        )
    }
}