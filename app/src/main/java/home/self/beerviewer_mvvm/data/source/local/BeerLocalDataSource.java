package home.self.beerviewer_mvvm.data.source.local;

import android.util.Log;
import java.util.List;


import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.rxbus.Events;
import home.self.beerviewer_mvvm.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.util.IndexUtil;
import io.reactivex.Single;


public class BeerLocalDataSource implements BeerDataSource{

    private BeerDao beerDao;

    public BeerLocalDataSource() {
        this.beerDao = BeerDatabase.getInstance().beerDao();
    }

    private void sendEventBus() {
        RxEventBus.getInstance().post(new Events.PageEvent());
    }

    @Override
    public void addBeers(List<BeerModel> beers) {
        List<BeerModel> previous = beerDao.getAllBeers();
        List<BeerModel> inserts = beers;

        beerDao.deleteBeers(previous);
        beerDao.insertBeers(inserts);
    }

    @Override
    public Single<List<BeerModel>> getBeers() {
        return null;
    }

    @Override
    public void getBeers(int pageStart, int perPage, BeerDataSource.LoadBeersCallback callback) {
        int indexStart;
        if (pageStart == 10) {
            indexStart = IndexUtil.getIndex(pageStart);
            sendEventBus();
        } else
            indexStart = IndexUtil.getIndex(pageStart);

        List<BeerModel> beers = beerDao.getBeers(indexStart, perPage);
        Log.d("123123s", pageStart + " , " + indexStart);
        Log.d("123123s", beers.size() + " !!");
        if (beers.size() != 0)
            callback.onTaskLoaded(beers);
        else
            callback.onDataNotAvailable();
    }

    @Override
    public void getBeer(int beerId, GetBeerCallback callback) {
        BeerModel beer = beerDao.getBeer(beerId);
        if (beer != null)
            callback.onBeerLoaded(beer);
        else
            callback.onDataNotAvailable();
    }
}













