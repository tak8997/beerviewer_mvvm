package home.self.beerviewer_mvvm.app_kotlin.data.source.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.rx.rxbus.Events;
import home.self.beerviewer_mvvm.rx.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.util.IndexUtil;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class BeerLocalDataSource implements BeerDataSource {

    private BeerDao beerDao;

    @Inject
    public BeerLocalDataSource(BeerDao beerDao) {
        this.beerDao = beerDao;
    }

    private void sendEventBus() {
        RxEventBus.getInstance().post(new Events.PageEvent());
    }

    @Override
    public void saveBeers(List<BeerModel> beers) {
        List<BeerModel> previous = beerDao.getAllBeers();
        List<BeerModel> inserts = beers;

        beerDao.deleteBeers(previous);
        beerDao.insertBeers(inserts);
    }

    @Override
    public Maybe<List<BeerModel>> getBeers() {
        return null;
    }

    @Override
    public Single<List<BeerModel>> getBeers(int pageStart, int perPage) {
        int indexStart;
        if (pageStart == 10) {
            indexStart = IndexUtil.getIndex(pageStart);
            sendEventBus();
        } else
            indexStart = IndexUtil.getIndex(pageStart);

        return beerDao.getBeers(indexStart, perPage);
    }

//    @Override
//    public void getBeers(int pageStart, int perPage, BeerDataSource.LoadBeersCallback callback) {
//        int indexStart;
//        if (pageStart == 10) {
//            indexStart = IndexUtil.getIndex(pageStart);
//            sendEventBus();
//        } else
//            indexStart = IndexUtil.getIndex(pageStart);
//
//        List<BeerModel> beers = beerDao.getBeers(indexStart, perPage);
//        Log.d("123123s", pageStart + " , " + indexStart);
//        Log.d("123123s", beers.size() + " !!");
//        if (beers.size() != 0)
//            callback.onTaskLoaded(beers);
//        else
//            callback.onDataNotAvailable();
//    }

    @Override
    public void getBeer(int beerId, GetBeerCallback callback) {
        BeerModel beer = beerDao.getBeer(beerId);
        if (beer != null)
            callback.onBeerLoaded(beer);
        else
            callback.onDataNotAvailable();
    }
}













