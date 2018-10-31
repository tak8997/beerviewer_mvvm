package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import androidx.lifecycle.MutableLiveData
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.Constants
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

internal interface BeersViewModel {

    interface Inputs {
        fun fetchBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
    }

    interface Outputs {
        fun fetchBeers(): Observable<List<BeerModel>>
        fun fetchIndex(): Observable<Int>
        fun isLoading(): Observable<Boolean>
    }

    class ViewModel @Inject constructor(
            @App private val repository: BeerRepositoryApi

    ): BaseViewModel(), Inputs, Outputs {

        val intpus = this
        val outpus = this

        private val beers = BehaviorSubject.create<List<BeerModel>>()
        private val isLoading = PublishSubject.create<Boolean>()
        private val index = PublishSubject.create<Int>()

        init {
            intent().map { it.getSerializableExtra(Constants.EXTRA_DEFAULT_PAGE) as Pair<Int, Int> }
                    .doOnNext { isLoading.onNext(true) }
                    .subscribeBy { it ->
                        fetchBeers(it.first, it.second, SwipyRefreshLayoutDirection.TOP)
                    }

            subscribeIndex()
        }

        override fun fetchBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
                = repository
                .fetchBeers(pageStart, perPage)
                .doOnNext { isLoading.onNext(false) }
                .flatMap { beers ->
                    Flowable.fromIterable(beers)
                            .doOnNext { beer -> beer.direction = direction }
                            .toList()
                            .toFlowable()
                }
                .subscribeBy { beerList ->
                    beers.onNext(beerList)
                }

        override fun fetchBeers(): Observable<List<BeerModel>> = beers

        override fun fetchIndex(): Observable<Int> = index

        override fun isLoading(): Observable<Boolean> = isLoading

        private fun subscribeIndex()
                = repository
                .getIndex()
                .subscribe { it ->
                    index.onNext(it)
                }
    }

}

