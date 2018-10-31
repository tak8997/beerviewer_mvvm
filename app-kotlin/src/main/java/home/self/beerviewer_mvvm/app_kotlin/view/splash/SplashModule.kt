package home.self.beerviewer_mvvm.app_kotlin.view.splash

import androidx.lifecycle.ViewModel
import com.mashup.dutchmarket.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module(includes = arrayOf(SplashModule.ProvideModule::class))
internal interface SplashModule {

    @Module
    class ProvideModule {

    }

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.ViewModel::class)
    fun bindSplashViewModel(splashViewModel: SplashViewModel.ViewModel): ViewModel

}
