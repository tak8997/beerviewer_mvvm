package home.self.beerviewer_mvvm.data.source;

import android.util.Log;


import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Singleton
public class BeerRepository implements BeerDataSource {

    private static final String TAG = BeerRepository.class.getSimpleName();

    private BeerDataSource beerRemoteDataSource;
    private BeerDataSource beerLocalDataSource;
    private boolean isCache = true;

    @Inject
    public BeerRepository(@Remote BeerDataSource beerRemoteDataSource,
                          @Local BeerDataSource beerLocalDataSource) {
        this.beerRemoteDataSource = beerRemoteDataSource;
        this.beerLocalDataSource = beerLocalDataSource;
    }

    @Override
    public Flowable<BeerModel> getBeer(final int beerId) {
        return beerLocalDataSource
                .getBeer(beerId)
                .switchMap(beerModel -> {
                    if (beerModel == null)
                        return getBeerFromRemote(beerId);
                    else
                        return Flowable.just(beerModel);
                });
    }

    private Flowable<BeerModel> getBeerFromRemote(int beerId) {
        return beerRemoteDataSource
                .getBeer(beerId)
                .filter(beer -> {
                    if (beer != null) {
                        saveBeer(beer);
                        return true;
                    } else
                        return false;
                });
    }

    @Override
    public void saveBeer(BeerModel beer) {
        beerLocalDataSource.saveBeer(beer);
    }

    @Override
    public void saveBeers(List<BeerModel> beers) {
        beerLocalDataSource.saveBeers(beers);
    }

    @Override
    public Maybe<List<BeerModel>> getBeers() {
        return beerRemoteDataSource.getBeers()
                .filter(beers-> {
                    if (!beers.isEmpty()) {
                        saveBeers(beers);    //save local cache
                        return true;
                    } else
                        return false;
                });
    }

    /**
     * local cache check
     * @param pageStart
     * @param perPage
     */
    @Override
    public Flowable<List<BeerModel>> getBeers(int pageStart, int perPage) {
        return beerLocalDataSource.getBeers(pageStart, perPage)
                .filter(beers-> !beers.isEmpty())
                .switchMap(beerModels -> {
                    if (beerModels.isEmpty())
                        return getBeersFromRemote(pageStart, perPage);
                    else
                        return Flowable.just(beerModels).fromIterable(beerModels).toList().toFlowable();
                });
    }

    private Flowable<List<BeerModel>> getBeersFromRemote(int pageStart, int perPage) {
        return beerRemoteDataSource.getBeers(pageStart, perPage)
                .filter(beers-> {
                    if (!beers.isEmpty()) {
                        saveBeers(beers);
                        return true;
                    } else
                        return false;
                });
    }
}
