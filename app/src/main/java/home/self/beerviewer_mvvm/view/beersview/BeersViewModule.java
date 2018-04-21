package home.self.beerviewer_mvvm.view.beersview;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.di.ActivityScope;

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
public class BeersViewModule {


    @Provides
    @ActivityScope
    BeersViewModel provideBeersViewModel(BeerRepository beerRepository) {
        return new BeersViewModel(beerRepository);
    }
}
