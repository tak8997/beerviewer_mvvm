package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.extensions.runOnIoThread
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

class BeersViewModel(
        val repository: BeerRepository,
        val schedulerProvider: BaseSchedulerProvider)
    : ViewModel() {

    val swipeDirection : BehaviorSubject<SwipyRefreshLayoutDirection>
            = BehaviorSubject.create()

    fun getBeers(pageStart: Int, perPage: Int, direction: SwipyRefreshLayoutDirection): Flowable<List<BeerModel>>
            = repository.getBeers(pageStart, perPage)


}