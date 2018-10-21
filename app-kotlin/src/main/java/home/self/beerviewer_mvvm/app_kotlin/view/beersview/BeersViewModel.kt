package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModel
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

internal interface BeersViewModel {

    class ViewModel @Inject constructor(
            @App val repository: BeerRepositoryApi

    ): BaseViewModel() {

        val beers: BehaviorSubject<List<BeerModel>> = BehaviorSubject.create()

        fun getBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
                = repository
                .getBeers(pageStart, perPage)
//                .flatMap { beers -> Flowable.fromIterable(beers)
//                        .doOnNext { beer -> beer.direction = direction }
//                        .toList().toFlowable()
//                }
//                .subscribeOn(schedulerProvider.io())
//                .observeOn(schedulerProvider.ui())
                .subscribe { beerList ->
                    beers.onNext(beerList)
                }

        fun getIndex(): Observable<Int> = repository.getIndex()
    }

}

