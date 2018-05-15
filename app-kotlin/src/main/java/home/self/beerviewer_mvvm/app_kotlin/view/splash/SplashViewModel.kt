package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.arch.lifecycle.ViewModel
import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.util.SupportOptional
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

class SplashViewModel(
        val repostory : BeerRepository,
        val schedulerProvider: BaseSchedulerProvider)
    : ViewModel() {

    val message : BehaviorSubject<String> = BehaviorSubject.create()
    val isSplashLoading : BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getBeers() : Disposable
            = repostory.getBeers()
            .doOnSubscribe { isSplashLoading.onNext(true) }
            .doOnTerminate { isSplashLoading.onNext(false) }
            .subscribeOn(schedulerProvider.io())
            .subscribe({it ->
                if (it.isEmpty())
                    message.onNext(BeerViewerApplication.instance.getString(R.string.no_data))
            })
}
