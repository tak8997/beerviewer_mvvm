package home.self.beerviewer_mvvm.app_kotlin.data.source;


import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface BeerDataSource {

    interface LoadBeersCallback {

        void onTaskLoaded(List<BeerModel> beers);

        void onDataNotAvailable();
    }

    interface GetBeerCallback {

        void onBeerLoaded(BeerModel beer);

        void onDataNotAvailable();
    }

    void saveBeers(List<BeerModel> beers);

    Maybe<List<BeerModel>> getBeers();

    Single<List<BeerModel>> getBeers(int pageStart, int perPage);

//    void getBeers(int pageStart, int perPage, LoadBeersCallback callback);

    void getBeer(int beerId, GetBeerCallback callback);
}
