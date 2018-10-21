package home.self.beerviewer_mvvm.app_kotlin.di


import com.mashup.dutchmarket.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailActivity
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailModule
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewModule
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashActivity
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashModule

@Module
internal abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(SplashModule::class))
    @ActivityScope
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(BeersViewModule::class))
    @ActivityScope
    abstract fun beersViewActivity() : BeersViewActivity

    @ContributesAndroidInjector(modules = arrayOf(BeerDetailModule::class))
    @ActivityScope
    abstract fun beerDetailActivity() : BeerDetailActivity
}
