package home.self.beerviewer_mvvm.view.splash;

import androidx.databinding.BaseObservable;

import javax.inject.Inject;

import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class SplashViewModel extends BaseObservable {

    private SplashNavigator splashView;

    private BeerRepository beerRepository;
    private BaseSchedulerProvider schedulerProvider;

    private Disposable disposable;

    @Inject
    public SplashViewModel(BeerRepository beerRepository, BaseSchedulerProvider schedulerProvider) {
        this.beerRepository = beerRepository;
        this.schedulerProvider = schedulerProvider;
    }

    public void subscribe() {
        getBeers();
    }

    public void getBeers() {
        disposable = beerRepository.getBeers()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(beerModels -> splashView.startBeerViewActivity(),
                        throwable -> splashView.showFailureMessage(BeerViewerApplication.getInstance().getString(R.string.no_data)));
    }

    public void takeView(SplashNavigator splashView) {
        this.splashView = splashView;
        this.splashView.showSplashAnimation();
    }


    public void unsubscribe() {
        disposable.dispose();
    }
}
