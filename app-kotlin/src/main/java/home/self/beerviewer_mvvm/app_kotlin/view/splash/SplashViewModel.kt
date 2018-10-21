package home.self.beerviewer_mvvm.app_kotlin.view.splash

import android.arch.lifecycle.MutableLiveData
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

internal interface SplashViewModel {

    interface Inputs

    interface Outputs {
        fun isSplashLoadingEnd(): MutableLiveData<Boolean>
        fun errorMessage(): MutableLiveData<String>
    }

    class ViewModel @Inject constructor(
            @App private val repostory : BeerRepositoryApi

    ): BaseViewModel(), Inputs, Outputs {

        val inputs = this
        val outputs = this

        private val message = MutableLiveData<String>()
        private val splashLoading = MutableLiveData<Boolean>()

        init {
            compositeDisposable.addAll(
                    repostory
                            .getBeers(1, 25)
                            .doOnError {
                                message.postValue("알 수 없는 에러입니다. 다시 시도해주세요.")
                            }
                            .subscribeBy {
                                splashLoading.postValue(true)
                            }
            )
        }

        override fun isSplashLoadingEnd(): MutableLiveData<Boolean> = splashLoading

        override fun errorMessage(): MutableLiveData<String> = message
    }
}
