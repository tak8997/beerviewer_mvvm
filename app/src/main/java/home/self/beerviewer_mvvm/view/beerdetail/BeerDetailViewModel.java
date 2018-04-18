package home.self.beerviewer_mvvm.view.beerdetail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;

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

    private String beerInfo;

    public BeerDetailViewModel(BeerDataSource beerRepository, BeerDetailNavigator beerDetailView) {
        this.beerRepository = beerRepository;
        this.beerDetailView = beerDetailView;
    }

    public void getBeer(int beerId) {
        if (beerId != -1) {
            beerRepository.getBeer(beerId, new BeerDataSource.GetBeerCallback() {
                @Override
                public void onBeerLoaded(BeerModel beer) {
                    showDetailBeer(beer);

                    appendBeerContent(beer);
                }

                @Override
                public void onDataNotAvailable() {
                    beerDetailView.showFailureMessage(BeerViewerApplication.getInstance().getString(R.string.cannot_load_data));
                }
            });
        }
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
}
