package home.self.beerviewer_mvvm.app_kotlin.view.splash

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.data.source.BeerRepository
import home.self.beerviewer_mvvm.di.ActivityScope
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
class SplashModule {

    @Provides
    @ActivityScope
    internal fun provideSplashViewModel(beerRepository: BeerRepository, schedulerProvider: BaseSchedulerProvider): SplashViewModel {
        return SplashViewModel(beerRepository, schedulerProvider)
    }
}
