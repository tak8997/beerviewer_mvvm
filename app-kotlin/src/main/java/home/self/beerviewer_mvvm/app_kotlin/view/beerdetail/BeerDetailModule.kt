package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.arch.lifecycle.ViewModel
import com.mashup.dutchmarket.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import home.self.beerviewer_mvvm.app_kotlin.Constant

/**
 * Created by Tak on 2018. 5. 16..
 */

@Module(includes = arrayOf(BeerDetailModule.ProvideModule::class))
internal interface BeerDetailModule {

    @Module
    class ProvideModule {

        @Provides
        fun provideBeerId(beerDetailActivity: BeerDetailActivity) : Int = beerDetailActivity.intent.getIntExtra(Constant.KEY_BEAR_ID, -1)
    }

    @Binds
    @IntoMap
    @ViewModelKey(BeerDetailViewModel.ViewModel::class)
    fun bindBeerDetailViewModel(beerDetailViewModel: BeerDetailViewModel.ViewModel): ViewModel
}
