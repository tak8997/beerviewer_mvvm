package home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.app.AppCompatActivity
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