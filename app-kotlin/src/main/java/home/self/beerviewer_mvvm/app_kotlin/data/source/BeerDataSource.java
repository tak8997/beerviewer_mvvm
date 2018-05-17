package home.self.beerviewer_mvvm.app_kotlin.data.source;


import java.util.List;

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel;
import io.reactivex.Flowable;

public interface BeerDataSource {

    void saveBeers(List<BeerModel> beers);

    Flowable<List<BeerModel>> getBeers();

    Flowable<List<BeerModel>> getBeers(int pageStart, int perPage);

    Flowable<BeerModel> getBeer(int beerId);

    void saveBeer(BeerModel beer);

    io.reactivex.Observable<Integer> getIndex();
}
