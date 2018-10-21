package home.self.beerviewer_mvvm.app_kotlin.view.splash

import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

internal interface SplashViewModel {

    class ViewModel @Inject constructor(
            val repostory : BeerRepositoryApi,
            val schedulerProvider: BaseSchedulerProvider

    ): BaseViewModel()  {

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
}
