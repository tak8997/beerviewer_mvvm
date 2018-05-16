package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider

/**
 * Created by Tak on 2018. 5. 16..
 */

@Module
class BeersViewModule {

    @Provides
    fun provideViewModelFactory(beerRepository: BeerRepository,
                                schedulerProvider: BaseSchedulerProvider) : BeersViewModelFactory
            = BeersViewModelFactory(beerRepository, schedulerProvider)

    @Provides
    fun provideBeersAdapter(activity: BeersViewActivity) : BeersAdapter
            = BeersAdapter().apply { setOnItemClickListener(activity) }
}