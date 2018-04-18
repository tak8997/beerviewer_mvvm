package home.self.beerviewer_mvvm.view.beersview;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.view.beerdetail.BeerDetailActivity;

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
