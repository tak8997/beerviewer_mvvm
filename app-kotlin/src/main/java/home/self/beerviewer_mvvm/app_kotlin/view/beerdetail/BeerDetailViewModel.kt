package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import home.self.beerviewer_mvvm.app_kotlin.base.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.base.Constants
import home.self.beerviewer_mvvm.app_kotlin.base.Parameter
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
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
        fun message(): Observable<String>
        fun isLoading(): Observable<Boolean>
    }

    class ViewModel @Inject constructor(
            @App val repository: BeerRepositoryApi

    ) : BaseViewModel(), Inputs, Outputs {

        val inputs = this
        private val clickBeerInfo = PublishSubject.create<Parameter>()

        val outputs = this
        private val beer = BehaviorSubject.create<BeerModel>()
        private val beerInfo = PublishSubject.create<String>()
        private val isLoading = PublishSubject.create<Boolean>()
        private val message = PublishSubject.create<String>()

        init {
            intent().map { it.getIntExtra(Constants.KEY_BEAR_ID, -1) }
                    .doOnNext{ isLoading.onNext(true) }
                    .filter { beerId -> beerId != -1 }
                    .flatMapMaybe { beerId ->
                        repository.fetchBeer(beerId).firstElement()
                    }
                    .doOnError { message.onNext(it.message ?: "unexpected error") }
                    .doOnNext{ isLoading.onNext(false) }
                    .subscribeBy { beerItem ->
                        beer.onNext(beerItem)
                    }

            beer.compose<BeerModel> { clickBeerInfo.withLatestFrom(it, BiFunction { _, t2 -> t2 }) }
                    .map { beer ->
                        beer.name + ",\n" + beer.brewersTips + ",\n" + beer.firstBrewed
                    }
                    .doOnNext { isLoading.onNext(true) }
                    .subscribeBy {
                        beerInfo.onNext(it)
                    }
        }

        override fun clickBeerInfo(click: Parameter) = clickBeerInfo.onNext(click)


        override fun fetchBeer(): Observable<BeerModel> = beer

        override fun fetchBeerInfo(): Observable<String> = beerInfo

        override fun message(): Observable<String> = message

        override fun isLoading(): Observable<Boolean> = isLoading

    }
}
