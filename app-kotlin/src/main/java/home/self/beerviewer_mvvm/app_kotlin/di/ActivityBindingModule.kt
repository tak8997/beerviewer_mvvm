package home.self.beerviewer_mvvm.app_kotlin.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashActivity
import home.self.beerviewer_mvvm.app_kotlin.view.splash.SplashModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(SplashModule::class))
    abstract fun splashActivity(): SplashActivity

    //    @ContributesAndroidInjector(modules = BeersViewModule.class)
    //    @ActivityScope
    //    abstract BeersViewActivity beerViewActivity();
    //
    //    @ContributesAndroidInjector(modules = BeerDetailModule.class)
    //    @ActivityScope
    //    abstract BeerDetailActivity beerDetailActivity();
}
