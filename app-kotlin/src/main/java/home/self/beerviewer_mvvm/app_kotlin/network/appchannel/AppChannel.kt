package home.self.beerviewer_mvvm.app_kotlin.network.appchannel

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import home.self.beerviewer_mvvm.app_kotlin.data.model.RemoteCallState
import io.reactivex.Observable
import javax.inject.Inject

internal class AppChannel @Inject constructor(): AppChannelApi {

    private val remoteCallStateChannel: Relay<RemoteCallState> = PublishRelay.create()

    override fun ofRemoteCallState(): Observable<RemoteCallState> = remoteCallStateChannel

    override fun accept(remoteCallState: RemoteCallState) = remoteCallStateChannel.accept(remoteCallState)

}
