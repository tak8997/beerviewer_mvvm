package home.self.beerviewer_mvvm.view.beerdetail;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.di.ActivityScope;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;

/**
 * Created by Tak on 2018. 4. 19..
 */

@Module
public class BeerDetailModule {

    @Provides
    @ActivityScope
    BeerDetailViewModel provideBeerDetailViewModel(BeerRepository beerRepository, BaseSchedulerProvider schedulerProvider, int beerId) {
        return new BeerDetailViewModel(beerRepository, schedulerProvider, beerId);
    }

    @Provides
    @ActivityScope
    static int provideBeerId(BeerDetailActivity activity) {
        return activity.getIntent().getIntExtra(Constant.KEY_BEAR_ID, -1);
    }
}
