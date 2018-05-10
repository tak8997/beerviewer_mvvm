package home.self.beerviewer_mvvm.data.source.remote;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.network.BeerApiService;
import home.self.beerviewer_mvvm.network.BeerViewerClient;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class BeerRemoteDataSource implements BeerDataSource {

    @Inject
    public BeerRemoteDataSource(BeerApiService apiService) {
        this.apiService = apiService;
    }

    private final BeerApiService apiService;

    @Override
    public void saveBeers(List<BeerModel> beers) { }

    @Override
    public Maybe<List<BeerModel>> getBeers() {
        return apiService.getBeers();
    }

    @Override
    public void getBeers(int pageStart, int perPage, final BeerDataSource.LoadBeersCallback callback) {
        apiService
                .getBeers(pageStart, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BeerModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BeerModel> beerModels) {
                        if (beerModels.size() == 0)
                            callback.onDataNotAvailable();
                        else
                            callback.onTaskLoaded(beerModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getBeer(int beerId, GetBeerCallback callback) {

    }

}
