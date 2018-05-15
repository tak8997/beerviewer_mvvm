package home.self.beerviewer_mvvm.app_kotlin.data.source.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel;
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.app_kotlin.rx.rxbus.Events;
import home.self.beerviewer_mvvm.app_kotlin.rx.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.app_kotlin.util.IndexUtil;
import io.reactivex.Flowable;
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
    public void saveBeer(BeerModel beer) {
        beerDao.insertBeer(beer);
    }

    @Override
    public Maybe<List<BeerModel>> getBeers() {
        return null;
    }

    @Override
    public Flowable<List<BeerModel>> getBeers(int pageStart, int perPage) {
        int indexStart;
        if (pageStart == 10) {
            indexStart = IndexUtil.getIndex(pageStart);
            sendEventBus();
        } else
            indexStart = IndexUtil.getIndex(pageStart);

        return beerDao.getBeers(indexStart, perPage);
    }

    @Override
    public Flowable<BeerModel> getBeer(int beerId) {
        return beerDao.getBeer(beerId);
    }


}













