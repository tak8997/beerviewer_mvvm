package home.self.beerviewer_mvvm.app_kotlin.data.source;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class BeerRepository implements BeerDataSource {

    private static final String TAG = BeerRepository.class.getSimpleName();

    private BeerDataSource beerRemoteDataSource;
    private BeerDataSource beerLocalDataSource;

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
    public Flowable<List<BeerModel>> getBeers() {
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
                .switchMap(beerModels -> {                                     //if local is empty, get from remote
                    if (beerModels.isEmpty())
                        return getBeersFromRemote(pageStart, perPage);
                    else
                        return Flowable.just(beerModels);
                })
                .firstElement()
                .toFlowable();
    }

    private Flowable<List<BeerModel>> getBeersFromRemote(int pageStart, int perPage) {
        return beerRemoteDataSource.getBeers(pageStart, perPage)
                .filter(beers-> {
                    if (!beers.isEmpty()) {
                        saveBeers(beers);
                        return true;
                    } else
                        return false;
                })
                .firstElement()
                .toFlowable();
    }

    @NotNull
    public Observable<Integer> getIndex() {
        return beerLocalDataSource.getIndex();
    }
}
