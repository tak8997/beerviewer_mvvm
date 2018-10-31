package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import androidx.lifecycle.ViewModel
import com.mashup.dutchmarket.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by Tak on 2018. 5. 16..
 */

@Module(includes = arrayOf(BeersViewModule.ProvideModule::class))
internal interface BeersViewModule {

    @Module
    class ProvideModule {

        @Provides
        fun provideBeersAdapter(activity: BeersViewActivity) : BeersAdapter = BeersAdapter().apply { setOnItemClickListener(activity) }
    }

    @Binds
    @IntoMap
    @ViewModelKey(BeersViewModel.ViewModel::class)
    fun bindBeersViewModel(beersViewModel: BeersViewModel.ViewModel): ViewModel
}