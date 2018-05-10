package home.self.beerviewer_mvvm.view.beersview;

import android.databinding.BaseObservable;

import java.util.List;

import javax.inject.Inject;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.rx.rxbus.Events;
import home.self.beerviewer_mvvm.rx.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;
import home.self.beerviewer_mvvm.rx.schedulers.SchedulerProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BeersViewModel extends BaseObservable {

    private BeerRepository beerRepository;
    private BaseSchedulerProvider schedulerProvider;

    private BeersViewNavigator beersView;

    private CompositeDisposable compositeDisposable;
    private Disposable disposable;

    @Inject
    public BeersViewModel(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
        this.schedulerProvider = new SchedulerProvider();

        this.compositeDisposable = new CompositeDisposable();
        onEventBusCalled();
    }

    public void getBeers(int pageStart, int perPage) {
        beerRepository.getBeers(pageStart, perPage, new BeerDataSource.LoadBeersCallback() {
            @Override
            public void onTaskLoaded(List<BeerModel> beers) {
                beersView.showItems(beers);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void getBeersFromBottom(int pageStart, int perPage, int position) {
        beerRepository.getBeers(pageStart, perPage, new BeerDataSource.LoadBeersCallback() {
            @Override
            public void onTaskLoaded(List<BeerModel> beers) {
                beersView.showItemsFromBottom(beers, position);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void onEventBusCalled() {
        disposable = RxEventBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event instanceof Events.PageEvent)
                        beersView.setPageStart();
                });
    }

    public void takeView(BeersViewNavigator beersView) {
        this.beersView = beersView;
    }
}

