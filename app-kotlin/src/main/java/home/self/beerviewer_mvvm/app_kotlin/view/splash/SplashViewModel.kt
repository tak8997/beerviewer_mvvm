package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.arch.lifecycle.MutableLiveData
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal interface SplashViewModel {

    interface Inputs

    interface Outputs {
        fun isSplashLoadingEnd(): MutableLiveData<Pair<Int, Int>>
        fun errorMessage(): MutableLiveData<String>
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

        private val message = MutableLiveData<String>()
        private val splashLoading = MutableLiveData<Pair<Int, Int>>()

        init {
            compositeDisposable.addAll(
                    repostory
                            .fetchBeers(1, 25)
                            .doOnError {
                                message.postValue("알 수 없는 에러입니다. 다시 시도해주세요.")
                            }
                            .subscribeBy {
                                splashLoading.postValue(Pair(DEFAULT_START_PAGE, DEFAULT_END_PAGE))
                            }
            )
        }

        override fun isSplashLoadingEnd(): MutableLiveData<Pair<Int, Int>> = splashLoading

        override fun errorMessage(): MutableLiveData<String> = message
    }
}
