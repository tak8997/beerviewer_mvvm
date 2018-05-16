package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.Constant
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider

/**
 * Created by Tak on 2018. 5. 16..
 */

@Module
class BeerDetailModule {

//    @Module
//    companion object {
//        @JvmStatic
        @Provides
        fun provideBeerId(beerDetailActivity: BeerDetailActivity) : Int
                = beerDetailActivity.intent.getIntExtra(Constant.KEY_BEAR_ID, -1)
//    }

    @Provides
    fun provideViewModelFactory(beerRepository: BeerRepository,
                                schedulerProvider: BaseSchedulerProvider,
                                beerId: Int) : BeerDetailViewModelFactory
        = BeerDetailViewModelFactory(beerRepository, schedulerProvider, beerId)
}
