package home.self.beerviewer_mvvm.app_kotlin.extensions

import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun runOnIoThread(schedulerIO : Scheduler, func : ()->Unit) : Disposable {
    if (Schedulers.io() == schedulerIO)
        return Completable.fromCallable(func).subscribeOn(schedulerIO).subscribe()
    else
        throw IllegalAccessException("must be a IO scheduler")
}