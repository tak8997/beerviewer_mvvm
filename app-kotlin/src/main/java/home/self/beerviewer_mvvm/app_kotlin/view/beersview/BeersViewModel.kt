package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.Disposable

class BeersViewModel(
        val repository: BeerRepository,
        val schedulerProvider: BaseSchedulerProvider)
    : ViewModel() {

    fun getBeers(pageStart: Int, perPage: Int, top: SwipyRefreshLayoutDirection): Disposable {

    }


}