package home.self.beerviewer_mvvm.view.splash;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.di.ActivityScope;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
public class SplashModule {

    @Provides
    @ActivityScope
    SplashViewModel provideSplashViewModel(BeerRepository beerRepository, BaseSchedulerProvider schedulerProvider) {
        return  new SplashViewModel(beerRepository, schedulerProvider);
    }
}
