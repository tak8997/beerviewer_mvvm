package home.self.beerviewer_mvvm.view.splash;

import android.databinding.BaseObservable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseObservable {

    private SplashNavigator splashView;
    private BeerRepository beerRepository;

    @Inject
    public SplashViewModel(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }


    public void getBeers() {
        beerRepository.getBeers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BeerModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(List<BeerModel> beerModels) {
                        List<BeerModel> beers = beerModels;
                        Log.d("SplashActivity", "beer_size : " + beers.size() + "");
                        if(beers != null) {
                            beerRepository.addBeers(beers);
                            splashView.startBeerViewActivity();
                        } else
                            splashView.showFailureMessage(BeerViewerApplication.getInstance().getString(R.string.no_data));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void takeView(SplashNavigator splashView) {
        this.splashView = splashView;
        this.splashView.showSplashAnimation();
    }
}
