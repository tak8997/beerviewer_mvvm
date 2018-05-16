package home.self.beerviewer_mvvm.app_kotlin.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailActivity
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailModule
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewActivity
import home.self.beerviewer_mvvm.app_kotlin.view.beersview.BeersViewModule
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashActivity
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(SplashModule::class))
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(BeersViewModule::class))
    abstract fun beersViewActivity() : BeersViewActivity

    @ContributesAndroidInjector(modules = arrayOf(BeerDetailModule::class))
    abstract fun beerDetailActivity() : BeerDetailActivity
}
