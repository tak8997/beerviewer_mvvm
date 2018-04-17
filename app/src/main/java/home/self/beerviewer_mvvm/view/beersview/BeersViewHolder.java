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

    @BindView(R.id.beer_img)
    ImageView image;

    @BindView(R.id.beer_title)
    TextView tvTitle;

    @BindView(R.id.id)
    TextView tvId;

    @BindView(R.id.beer_tagline)
    TextView tvTagline;

    @BindView(R.id.beer_first_brewed)
    TextView tvFirstBrewed;

    private BeerModel beer;

    public BeersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BeerDetailActivity.class);
                intent.putExtra(Constant.KEY_BEAR_ID, beer.getId());
                view.getContext().startActivity(intent);
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(view.getContext(), R.anim.slide_right_in, R.anim.slide_left_out);
//                ActivityCompat.startActivity(view.getContext(), intent, options.toBundle());
            }
        });
    }

    public void bindItem(final BeerModel beerModel) {
        this.beer = beerModel;

        Glide.with(BeerViewerApplication.getInstance())
                .load(beerModel.getImageUrl())
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);
        tvId.setText(beer.getId() + "");
        tvTitle.setText(beerModel.getName());
        tvTagline.setText(beerModel.getTagline());
        tvFirstBrewed.setText("제조날짜 : " + beerModel.getFirstBrewed());
    }

}
