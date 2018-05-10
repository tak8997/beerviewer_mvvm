package home.self.beerviewer_mvvm.view.beersview;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.di.ActivityScope;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
public class BeersViewModule {


    @Provides
    @ActivityScope
    BeersViewModel provideBeersViewModel(BeerRepository beerRepository, BaseSchedulerProvider schedulerProvider) {
        return new BeersViewModel(beerRepository, schedulerProvider);
    }
}
