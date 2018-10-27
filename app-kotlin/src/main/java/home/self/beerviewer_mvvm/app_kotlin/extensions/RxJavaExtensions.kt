package home.self.beerviewer_mvvm.app_kotlin.extensions

import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException

fun runOnIoThread(schedulerIO : Scheduler, func : () -> Unit) : Disposable {
    if (Schedulers.io() == schedulerIO)
        return Completable.fromCallable(func).subscribeOn(schedulerIO).subscribe()
    else
        throw IllegalArgumentException("must be a IO scheduler")
}

fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}