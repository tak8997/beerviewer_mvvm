package home.self.beerviewer_mvvm.data.source;

import android.util.Log;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import io.reactivex.Single;

public class BeerRepository implements BeerDataSource {

    private static final String TAG = BeerRepository.class.getSimpleName();

    private BeerDataSource beerRemoteDataSource;
    private BeerDataSource beerLocalDataSource;
    private boolean isCache = true;

    public BeerRepository(BeerDataSource beerRemoteDataSource,
                          BeerDataSource beerLocalDataSource) {
        this.beerRemoteDataSource = beerRemoteDataSource;
        this.beerLocalDataSource = beerLocalDataSource;
    }


    @Override
    public void getBeer(final int beerId, final GetBeerCallback callback) {
        beerLocalDataSource.getBeer(beerId, new GetBeerCallback() {
            @Override
            public void onBeerLoaded(BeerModel beer) {
                Log.d(TAG, "get beer local call");
                callback.onBeerLoaded(beer);
            }

            @Override
            public void onDataNotAvailable() {
                beerRemoteDataSource.getBeer(beerId, new GetBeerCallback() {
                    @Override
                    public void onBeerLoaded(BeerModel beer) {
                        //TODO : do memory local cache
                        Log.d(TAG, "get beer remote call");
                        callback.onBeerLoaded(beer);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Log.d(TAG, "get beer remote call fail");
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void addBeers(List<BeerModel> beers) {
        beerLocalDataSource.addBeers(beers);
    }

    @Override
    public Single<List<BeerModel>> getBeers() {
        return beerRemoteDataSource.getBeers();
    }

    /**
     * local cache check
     * @param pageStart
     * @param perPage
     * @param callback
     */
    @Override
    public void getBeers(final int pageStart, final int perPage, final LoadBeersCallback callback) {
        beerLocalDataSource.getBeers(pageStart, perPage, new LoadBeersCallback() {
            @Override
            public void onTaskLoaded(List<BeerModel> beers) {
                Log.d(TAG, "get beers local cache");
                callback.onTaskLoaded(beers);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "get beers remote call");
                beerRemoteDataSource.getBeers(pageStart, perPage, new LoadBeersCallback() {
                    @Override
                    public void onTaskLoaded(List<BeerModel> beers) {
                        callback.onTaskLoaded(beers);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        //TODO : 다시 local로 가서 첫번째 부터 보여줌.
                    }
                });
            }
        });

    }

}
