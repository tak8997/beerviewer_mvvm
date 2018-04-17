package home.self.beerviewer_mvvm.view.beersview;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.databinding.ActivityBeersViewBinding;
import home.self.beerviewer_mvvm.rxbus.Events;
import home.self.beerviewer_mvvm.rxbus.RxEventBus;
import home.self.beerviewer_mvvm.view.OnBottomReachedListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BeersViewActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnBottomReachedListener, BeersViewNavigator {

    private static final String TAG = BeersViewActivity.class.getSimpleName();

    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.beer_recyler)
    RecyclerView beerRecycler;

    @BindView(R.id.app_bar)
    Toolbar toolbar;

    private BeersViewModel beersViewModel;

    private BeersAdapter adapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public int pageStart = 1;
    private int perPage = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBeersViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_beers_view);
        beersViewModel = new BeersViewModel();
        binding.setViewModel(beersViewModel);

        initView();
        onEventBusCalled();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        adapter = new BeersAdapter();
        adapter.setOnBottomReachedListener(this);
        beerRecycler.setLayoutManager(new LinearLayoutManager(this));
        beerRecycler.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beersViewModel.getBeers(pageStart++, perPage);
                refreshLayout.setRefreshing(false);
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
