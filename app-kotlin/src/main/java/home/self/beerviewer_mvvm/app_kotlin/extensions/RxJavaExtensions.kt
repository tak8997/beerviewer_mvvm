package home.self.beerviewer_mvvm.app_kotlin.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun runOnIoThread(schedulerIO : Scheduler, func : () -> Unit) : Disposable {
    if (Schedulers.io() == schedulerIO)
        return Completable.fromCallable(func).subscribeOn(schedulerIO).subscribe()
    else
        throw IllegalArgumentException("must be a IO scheduler")
}

fun <T> applyFlowableAsysnc(): FlowableTransformer<T, T> {
    return FlowableTransformer { flowable ->
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> applySingleAsysnc(): SingleTransformer<T, T> {
    return SingleTransformer { flowable ->
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> Observable<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable(io.reactivex.BackpressureStrategy.DROP))

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = observe({ lifecycleOwner.lifecycle }, observer)

fun <T> Observable<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) = toLiveData().observe(lifecycleOwner, observer)