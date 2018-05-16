package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider

/**
 * Created by Tak on 2018. 5. 16..
 */

class BeerDetailViewModelFactory(val repository: BeerRepository,
                                 val schedulerProvider: BaseSchedulerProvider,
                                 val beerId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BeerDetailViewModel(repository, schedulerProvider, beerId) as T
    }
}
