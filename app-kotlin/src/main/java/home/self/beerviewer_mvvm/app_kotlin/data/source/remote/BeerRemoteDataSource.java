package home.self.beerviewer_mvvm.app_kotlin.data.source.remote;


import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel;
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService;
import io.reactivex.Flowable;

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
    public Flowable<List<BeerModel>> getBeers(int pageStart, int perPage) {
        Log.d("zxcv111", "a");
        return apiService.getBeers(pageStart, perPage);
    }

    @Override
    public Flowable<BeerModel> getBeer(int beerId) {
        return apiService.getBeer(beerId).toFlowable();
    }

    @Override
    public void saveBeer(BeerModel beer) { }
}
