package home.self.beerviewer_mvvm.app_kotlin.view.splash

import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

internal interface SplashViewModel {

    interface Inputs

    interface Outputs {
        fun isSplashLoadingEnd(): Observable<Pair<Int, Int>>
        fun errorMessage(): Observable<String>
    }

    class ViewModel @Inject constructor(
            @App private val repostory : BeerRepositoryApi

    ): BaseViewModel(), Inputs, Outputs {

        companion object {
            private const val DEFAULT_START_PAGE = 1
            private const val DEFAULT_END_PAGE = 25
        }

        val inputs = this
        val outputs = this

        private val message = PublishSubject.create<String>()
        private val splashLoading = PublishSubject.create<Pair<Int, Int>>()

        init {
            compositeDisposable.addAll(
                    repostory
                            .fetchBeers(1, 25)
                            .doOnError {
                                message.onNext("알 수 없는 에러입니다. 다시 시도해주세요.")
                            }
                            .subscribeBy {
                                splashLoading.onNext(Pair(DEFAULT_START_PAGE, DEFAULT_END_PAGE))
                            }
            )
        }

        override fun isSplashLoadingEnd(): Observable<Pair<Int, Int>> = splashLoading

        override fun errorMessage(): Observable<String> = message
    }
}
