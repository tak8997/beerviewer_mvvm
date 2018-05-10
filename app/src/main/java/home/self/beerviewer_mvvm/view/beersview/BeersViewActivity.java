package home.self.beerviewer_mvvm.view.beersview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.databinding.ActivityBeersViewBinding;
import home.self.beerviewer_mvvm.rx.rxbus.Events;
import home.self.beerviewer_mvvm.rx.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.view.OnBottomReachedListener;
import home.self.beerviewer_mvvm.view.beerdetail.BeerDetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BeersViewActivity extends DaggerAppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnBottomReachedListener, BeersViewNavigator {

    private static final String TAG = BeersViewActivity.class.getSimpleName();

//    @BindView(R.id.refreshlayout)
//    SwipeRefreshLayout refreshLayout;
//
//    @BindView(R.id.beer_recyler)
//    RecyclerView beerRecycler;
//
//    @BindView(R.id.app_bar)
//    Toolbar toolbar;

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
        onEventBusCalled();

        beersViewModel.takeView(this);
        beersViewModel.getBeers(pageStart++, perPage);
    }

    private void initView() {
        setSupportActionBar(binding.appBar);

        adapter = new BeersAdapter(this);
        adapter.setOnBottomReachedListener(this);
        binding.beerRecyler.setLayoutManager(new LinearLayoutManager(this));
        binding.beerRecyler.setAdapter(adapter);

        binding.refreshlayout.setOnRefreshListener(this);
        binding.refreshlayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN);
    }

    @Override
    public void showItems(List<BeerModel> beers) {
        adapter.addItems(beers);
    }

    @Override
    public void showItemsFromBottom(final List<BeerModel> beers, final int position) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                adapter.addItemsFromBottom(beers, position);
            }
        });
    }

    @Override
    public void startBeerDetailActivity(int beerId) {
        Intent intent = new Intent(this, BeerDetailActivity.class);
        intent.putExtra(Constant.KEY_BEAR_ID, beerId);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beersViewModel.getBeers(pageStart++, perPage);
                binding.refreshlayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onBottomReached(int position) {
        beersViewModel.getBeersFromBottom(pageStart++, perPage, position);
    }

    private void onEventBusCalled() {
        RxEventBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event instanceof Events.PageEvent)
                        pageStart = 1;
                });
    }
}
