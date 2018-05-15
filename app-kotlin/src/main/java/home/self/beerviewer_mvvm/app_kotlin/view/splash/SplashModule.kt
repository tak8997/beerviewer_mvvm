package home.self.beerviewer_mvvm.app_kotlin.view.splash

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
class SplashModule {

    @Provides
    fun provideViewModelFactory(beerRepository: BeerRepository,
                                schedulerProvider: BaseSchedulerProvider): SplashViewModelFactory
        = SplashViewModelFactory(beerRepository, schedulerProvider)
}
