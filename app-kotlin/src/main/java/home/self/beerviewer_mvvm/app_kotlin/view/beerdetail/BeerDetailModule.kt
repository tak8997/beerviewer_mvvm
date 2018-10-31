package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import androidx.lifecycle.ViewModel
import com.mashup.dutchmarket.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Tak on 2018. 5. 16..
 */

@Module(includes = arrayOf(BeerDetailModule.ProvideModule::class))
internal interface BeerDetailModule {

    @Module
    class ProvideModule

    @Binds
    @IntoMap
    @ViewModelKey(BeerDetailViewModel.ViewModel::class)
    fun bindBeerDetailViewModel(beerDetailViewModel: BeerDetailViewModel.ViewModel): ViewModel
}
