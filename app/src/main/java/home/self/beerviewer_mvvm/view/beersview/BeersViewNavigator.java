package home.self.beerviewer_mvvm.view.beersview;

import java.util.List;

import home.self.beerviewer_mvvm.data.model.BeerModel;

public interface BeersViewNavigator {
    void showItems(List<BeerModel> beers);

    void showItemsFromBottom(final List<BeerModel> beers, final int position);


}
