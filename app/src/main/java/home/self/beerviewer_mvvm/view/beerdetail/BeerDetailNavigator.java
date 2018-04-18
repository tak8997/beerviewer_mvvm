package home.self.beerviewer_mvvm.view.beerdetail;

import home.self.beerviewer_mvvm.data.model.BeerModel;

public interface BeerDetailNavigator {

    void showFailureMessage(String msg);

    void showShareDialog(String beerInfo);
}
