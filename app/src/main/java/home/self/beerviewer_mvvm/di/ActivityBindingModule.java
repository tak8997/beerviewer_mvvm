package home.self.beerviewer_mvvm.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import home.self.beerviewer_mvvm.view.beerdetail.BeerDetailActivity;
import home.self.beerviewer_mvvm.view.beerdetail.BeerDetailModule;
import home.self.beerviewer_mvvm.view.beersview.BeersViewActivity;
import home.self.beerviewer_mvvm.view.beersview.BeersViewModule;
import home.self.beerviewer_mvvm.view.splash.SplashActivity;
import home.self.beerviewer_mvvm.view.splash.SplashModule;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = SplashModule.class)
    @ActivityScope
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = BeersViewModule.class)
    @ActivityScope
    abstract BeersViewActivity beerViewActivity();

    @ContributesAndroidInjector(modules = BeerDetailModule.class)
    @ActivityScope
    abstract BeerDetailActivity beerDetailActivity();
}
