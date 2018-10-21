package home.self.beerviewer_mvvm.app_kotlin

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

typealias RequestCode = Int
typealias ResultCode = Int

internal abstract class BaseViewModel : ViewModel() {

    private val intentRelay = PublishRelay.create<Intent>()

    private val activityResultRelay = PublishRelay.create<Triple<Int, Int, Intent?>>()

    protected val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    @CallSuper
    fun intent(intent: Intent) {
        intentRelay.accept(intent)
    }

    fun intent(): Observable<Intent> = intentRelay

    fun onActivityResult(requestCode: RequestCode, resultCode: ResultCode, data: Intent?) {
        activityResultRelay.accept(Triple(requestCode, resultCode, data))
    }

    @CheckResult
    fun activityResult(): Observable<Triple<RequestCode, ResultCode, Intent?>> = activityResultRelay

    override fun onCleared() {
        compositeDisposable.dispose()

//        Timber.d("#onCleared")
        super.onCleared()
    }

    fun addDisposables(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }
}