package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashViewModel

class BeersViewModelFactory(val repository: BeerRepository,
                            val schedulerProvider: BaseSchedulerProvider)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BeersViewModel(repository, schedulerProvider) as T
    }
}