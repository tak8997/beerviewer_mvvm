package home.self.beerviewer_mvvm.view.beersview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.databinding.BeerItemBinding;
import home.self.beerviewer_mvvm.view.OnBottomReachedListener;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class BeersAdapter extends RecyclerView.Adapter<BeersViewHolder> {

    private List<BeerModel> items = new ArrayList<>();

    private BeersViewNavigator beersView;

    public BeersAdapter(BeersViewNavigator beersView) {
        this.beersView = beersView;
    }

    @Override
    public BeersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BeerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.beer_item, parent, false);
        binding.setViewModel(new BeerItemViewModel(beersView));
        return new BeersViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(BeersViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<BeerModel> beers) {
        this.items.addAll(0, beers);
        this.notifyDataSetChanged();
    }

    public void addItemsFromBottom(List<BeerModel> beers, int position) {
        this.items.addAll(position, beers);
        this.notifyDataSetChanged();
    }
}
