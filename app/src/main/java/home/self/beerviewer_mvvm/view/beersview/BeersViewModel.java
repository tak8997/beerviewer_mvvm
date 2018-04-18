package home.self.beerviewer_mvvm.view.beersview;

import android.databinding.BaseObservable;

import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerDataSource;
import home.self.beerviewer_mvvm.data.source.BeerRepository;

public class BeersViewModel extends BaseObservable {

    private BeerRepository beerRepository;
    private BeersViewNavigator beersView;

    public BeersViewModel(BeerRepository beerRepository, BeersViewNavigator beersView) {
        this.beerRepository = beerRepository;
        this.beersView = beersView;
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
}

