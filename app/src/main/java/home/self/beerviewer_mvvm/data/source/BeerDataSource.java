package home.self.beerviewer_mvvm.data.source;


import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface BeerDataSource {

    void saveBeers(List<BeerModel> beers);

    Maybe<List<BeerModel>> getBeers();

    Single<List<BeerModel>> getBeers(int pageStart, int perPage);

    Flowable<BeerModel> getBeer(int beerId);

    void saveBeer(BeerModel beer);
}
