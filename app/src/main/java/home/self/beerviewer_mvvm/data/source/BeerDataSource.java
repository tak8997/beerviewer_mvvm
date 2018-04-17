package home.self.beerviewer_mvvm.data.source;


import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
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

    void addBeers(List<BeerModel> beers);

    Single<List<BeerModel>> getBeers();

    void getBeers(int pageStart, int perPage, LoadBeersCallback callback);

    void getBeer(int beerId, GetBeerCallback callback);
}
