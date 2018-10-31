package home.self.beerviewer_mvvm.view.beersview;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import android.view.View;

import home.self.beerviewer_mvvm.data.model.BeerModel;

public class BeerItemViewModel extends BaseObservable {

    public ObservableField<String> imgBeer = new ObservableField<>();
    public ObservableField<String> tvTitle = new ObservableField<>();
    public ObservableField<String> tvId = new ObservableField<>();
    public ObservableField<String> tvTagline = new ObservableField<>();
    public ObservableField<String> tvFirstBrewed = new ObservableField<>();

    private BeersViewNavigator beersView;
    private int beerId;

    public BeerItemViewModel(BeersViewNavigator beersView) {
        this.beersView = beersView;
    }

    public void bindItem(BeerModel beer) {
        beerId = beer.getId();

        imgBeer.set(beer.getImageUrl());
        tvTitle.set(beer.getName());
        tvId.set(beerId+"");
        tvTagline.set(beer.getTagline());
        tvFirstBrewed.set(beer.getFirstBrewed());
    }

    public void onItemClick(View itemView) {
        beersView.startBeerDetailActivity(beerId);
    }
}
