package home.self.beerviewer_mvvm.view.beersview;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.databinding.ActivityBeersViewBinding;
import home.self.beerviewer_mvvm.view.beerdetail.BeerDetailActivity;

public class BeersViewActivity extends DaggerAppCompatActivity
        implements SwipyRefreshLayout.OnRefreshListener, BeersViewNavigator {

    private static final String TAG = BeersViewActivity.class.getSimpleName();

    @Inject
    BeersViewModel beersViewModel;
    private ActivityBeersViewBinding binding;

    private BeersAdapter adapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int pageStart = 1;
    private int perPage = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beers_view);
        binding.setViewModel(beersViewModel);

        initView();

        beersViewModel.takeView(this);
        beersViewModel.getBeers(pageStart++, perPage, SwipyRefreshLayoutDirection.TOP);
    }

    private void initView() {
        setSupportActionBar(binding.appBar);

        adapter = new BeersAdapter(this);
        binding.beerRecyler.setLayoutManager(new LinearLayoutManager(this));
        binding.beerRecyler.setAdapter(adapter);

        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN);
    }

    @Override
    public void showItems(List<BeerModel> beers) {
        adapter.addItems(beers);
    }

    @Override
    public void showItemsFromBottom(List<BeerModel> beers) {
        adapter.addItemsFromBottom(beers);
    }

    @Override
    public void startBeerDetailActivity(int beerId) {
        Intent intent = new Intent(this, BeerDetailActivity.class);
        intent.putExtra(Constant.KEY_BEAR_ID, beerId);
        startActivity(intent);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beersViewModel.getBeers(pageStart++, perPage, direction);
                binding.refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void setPageStart() {
        pageStart = 1;
    }
}
