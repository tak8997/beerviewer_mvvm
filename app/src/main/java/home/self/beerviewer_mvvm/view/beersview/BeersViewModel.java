package home.self.beerviewer_mvvm.view.beersview;

import android.databinding.BaseObservable;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import javax.inject.Inject;

import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.rx.rxbus.Events;
import home.self.beerviewer_mvvm.rx.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;
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
    public BeersViewModel(BeerRepository beerRepository, BaseSchedulerProvider schedulerProvider) {
        this.beerRepository = beerRepository;
        this.schedulerProvider = schedulerProvider;

        this.compositeDisposable = new CompositeDisposable();
        onEventBusCalled();
    }

    void getBeers(int pageStart, int perPage, SwipyRefreshLayoutDirection direction) {
        compositeDisposable.clear();
        Disposable disposable = beerRepository
                .getBeers(pageStart, perPage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(beers-> {
                    if (direction == SwipyRefreshLayoutDirection.TOP)
                        beersView.showItems(beers);
                    else if (direction == SwipyRefreshLayoutDirection.BOTTOM)
                        beersView.showItemsFromBottom(beers);
                });

        compositeDisposable.add(disposable);
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

