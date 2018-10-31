package home.self.beerviewer_mvvm.view.beersview;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import home.self.beerviewer_mvvm.data.model.BeerModel;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class BeersViewHolder extends RecyclerView.ViewHolder {

    private BeerItemViewModel viewModel;

    public BeersViewHolder(View itemView, BeerItemViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;
    }

    public void bindItem(BeerModel beer) {
        this.viewModel.bindItem(beer);
    }
}
