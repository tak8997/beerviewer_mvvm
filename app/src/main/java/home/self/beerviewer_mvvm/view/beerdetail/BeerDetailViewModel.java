package home.self.beerviewer_mvvm.view.beerdetail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BeerDetailViewModel extends BaseObservable {

    public ObservableField<String> beerImageUrl = new ObservableField<>();
    public ObservableField<String> tvTitle = new ObservableField<>();
    public ObservableField<String> tvDescription = new ObservableField<>();
    public ObservableField<String> tvBrewersTips = new ObservableField<>();
    public ObservableField<String> tvTagline = new ObservableField<>();
    public ObservableField<String> tvFirstBrewed = new ObservableField<>();
    public ObservableField<String> tvContributedBy = new ObservableField<>();
    public ObservableField<String> toolbarTitle = new ObservableField<>();

    private BeerDetailNavigator beerDetailView;

    private BeerDataSource beerRepository;
    private BaseSchedulerProvider schedulerProvider;
    private int beerId;
    private CompositeDisposable compositeDisposable;

    private String beerInfo;

    @Inject
    public BeerDetailViewModel(BeerDataSource beerRepository, BaseSchedulerProvider schedulerProvider, int beerId) {
        this.beerRepository = beerRepository;
        this.schedulerProvider = schedulerProvider;
        this.beerId = beerId;

        this.compositeDisposable = new CompositeDisposable();
    }

    public void subscribe() {
        getBeer();
    }

    public void getBeer() {
        if (beerId != -1) {
            compositeDisposable.clear();

            Disposable disposable = beerRepository.getBeer(beerId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(beer -> {
                        showDetailBeer(beer);
                        appendBeerContent(beer);
                    });
            compositeDisposable.add(disposable);
        }
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void showDetailBeer(BeerModel beer) {
        beerImageUrl.set(beer.getImageUrl());
        tvTitle.set(beer.getName());
        tvTagline.set(beer.getTagline());
        tvDescription.set(beer.getDescription());
        tvBrewersTips.set(beer.getBrewersTips());
        tvContributedBy.set(beer.getContributedBy());
        tvFirstBrewed.set(beer.getFirstBrewed());
        toolbarTitle.set(beer.getName());
    }

    private void appendBeerContent(BeerModel beer) {
        beerInfo = beer.getName() +"\n" + beer.getTagline() + "\n" + beer.getDescription() + "\n"
                + beer.getBrewersTips() + "\n" + beer.getContributedBy() + "\n" + beer.getFirstBrewed();
    }


    public void processBeerContent() {
        beerDetailView.showShareDialog(beerInfo);
    }

    public void takeView(BeerDetailNavigator beerDetailView) {
        this.beerDetailView = beerDetailView;
    }
}
