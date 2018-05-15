package home.self.beerviewer_mvvm.app_kotlin.data.source.remote;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel;
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

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
    public Flowable<List<BeerModel>> getBeers() {
        return apiService.getBeers();
    }

    @Override
    public Single<List<BeerModel>> getBeers(int pageStart, int perPage) {
        return apiService.getBeers(pageStart, perPage);
    }

    //    @Override
//    public void getBeers(int pageStart, int perPage, final BeerDataSource.LoadBeersCallback callback) {
//        apiService
//                .getBeers(pageStart, perPage)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<List<BeerModel>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<BeerModel> beerModels) {
//                        if (beerModels.size() == 0)
//                            callback.onDataNotAvailable();
//                        else
//                            callback.onTaskLoaded(beerModels);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//                });
//    }

    @Override
    public void getBeer(int beerId, BeerDataSource.GetBeerCallback callback) { }

}
