package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

class BeersViewModel(
        val repository: BeerRepository,
        val schedulerProvider: BaseSchedulerProvider)
    : ViewModel() {

    val beers: BehaviorSubject<List<BeerModel>> = BehaviorSubject.create()

    fun getBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Disposable
            = repository
            .getBeers(pageStart, perPage)
            .flatMap { beers -> Flowable.fromIterable(beers)
                    .doOnNext { beer -> beer.direction = direction }
                    .toList().toFlowable()
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { beerList ->
                beers.onNext(beerList)
            }




}

