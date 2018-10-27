package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.MutableLiveData
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.Constants
import home.self.beerviewer_mvvm.app_kotlin.Parameter
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Tak on 2018. 5. 16..
 */

internal interface BeerDetailViewModel {

    interface Inputs {
        fun clickBeerInfo(click: Parameter)
    }

    interface Outputs {
        fun fetchBeer(): Observable<BeerModel>
        fun fetchBeerInfo(): Observable<String>
        fun message(): MutableLiveData<String>
        fun isLoading(): MutableLiveData<Boolean>
    }

    class ViewModel @Inject constructor(
            @App val repository: BeerRepositoryApi

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs = this
        private val clickBeerInfo = PublishSubject.create<Parameter>()

        val outputs = this
        private val beer = PublishSubject.create<BeerModel>()
        private val beerInfo = PublishSubject.create<String>()
        private val isLoading = MutableLiveData<Boolean>()
        private val message = MutableLiveData<String>()

        init {
            intent().map { it.getIntExtra(Constants.KEY_BEAR_ID, -1) }
                    .doOnNext{ isLoading.postValue(true) }
                    .filter { beerId -> beerId != -1 }
                    .flatMapMaybe { beerId ->
                        repository.fetchBeer(beerId).firstElement()
                    }
                    .doOnError { message.postValue(it.message ?: "unexpected error") }
                    .doOnNext{ isLoading.postValue(false) }
                    .subscribeBy { beerItem ->
                        beer.onNext(beerItem)
                    }

            beer.compose<BeerModel> { clickBeerInfo.withLatestFrom(it, BiFunction { _, t2 -> t2 }) }
                    .map { beer ->
                        beer.name + ",\n" + beer.brewersTips + ",\n" + beer.firstBrewed
                    }
                    .doOnNext { isLoading.postValue(true) }
                    .subscribeBy {
                        beerInfo.onNext(it)
                    }
        }

        override fun clickBeerInfo(click: Parameter) = clickBeerInfo.onNext(click)


        override fun fetchBeer(): Observable<BeerModel> = beer

        override fun fetchBeerInfo(): Observable<String> = beerInfo

        override fun message(): MutableLiveData<String> = message

        override fun isLoading(): MutableLiveData<Boolean> = isLoading

    }
}
