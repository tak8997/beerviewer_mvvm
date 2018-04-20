package home.self.beerviewer_mvvm.view.beerdetail;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.di.ActivityScope;

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
public class BeerDetailModule {

    @Provides
    @ActivityScope
    BeerDetailViewModel provideBeerDetailViewModel(BeerRepository beerRepository) {
        return new BeerDetailViewModel(beerRepository);
    }
}
