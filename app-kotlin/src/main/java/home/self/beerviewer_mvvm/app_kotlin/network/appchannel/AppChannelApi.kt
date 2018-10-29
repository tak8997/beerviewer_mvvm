package home.self.beerviewer_mvvm.app_kotlin.network.appchannel

import home.self.beerviewer_mvvm.app_kotlin.data.model.RemoteCallState
import io.reactivex.Observable

internal interface AppChannelApi {

    fun ofRemoteCallState(): Observable<RemoteCallState>

    fun accept(remoteCallState: RemoteCallState)

}
