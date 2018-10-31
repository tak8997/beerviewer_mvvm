package home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

internal class AutoClearedDisposable (
        private val lifeycycleOwner : AppCompatActivity,
        private val clearOnStop : Boolean = true,
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()

) : LifecycleObserver {

    fun add(disposable: Disposable) {
        check(lifeycycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun cleanUp() {
        if (!clearOnStop && !lifeycycleOwner.isFinishing)
            return
        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachSelf() {
        compositeDisposable.clear()
        lifeycycleOwner.lifecycle.removeObserver(this)
    }
}