package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import dagger.android.support.DaggerAppCompatActivity
import home.self.beerviewer_mvvm.app_kotlin.Constant
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import kotlinx.android.synthetic.main.activity_beers_view.*
import javax.inject.Inject

class BeersViewActivity : DaggerAppCompatActivity(), SwipyRefreshLayout.OnRefreshListener {

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    @Inject lateinit var adapter: BeersAdapter
    @Inject lateinit var viewModelFactory : BeersViewModelFactory

    lateinit var viewModel : BeersViewModel

    private val handler : Handler = Handler(Looper.getMainLooper())
    private val schedulerProvider : BaseSchedulerProvider = SchedulerProvider()

    private var pageStart = 1
    private var perPage = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_view)

        initView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[BeersViewModel::class.java]

        lifecycle.addObserver(disposables)
        lifecycle.addObserver(viewDisposables)

        viewDisposables.add(viewModel.getBeers(pageStart, perPage, SwipyRefreshLayoutDirection.TOP)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {beers ->
                    adapter.addItems(beers)
                })

//        viewDisposables.add(viewModel.swipeDirection
//                .observeOn(schedulerProvider.ui())
//                .subscribe {direction ->
//                    if (direction == SwipyRefreshLayoutDirection.TOP) {
//                        beers?.let { adapter.addItems(it) }
//                    }
//                    else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
//                        beers?.let { adapter.addItemsFromBottom(it) }
//                    }
//                })
//
//        viewModel.swipeDirection.onNext(SwipyRefreshLayoutDirection.TOP)
    }

    private fun initView() {
        setSupportActionBar(app_bar)

        with(beer_recyler) {
            adapter = this@BeersViewActivity.adapter
            layoutManager = LinearLayoutManager(this@BeersViewActivity)
        }

        refresh_layout.setOnRefreshListener(this)
        refresh_layout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN)
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection) {
        handler.postDelayed({
            viewModel.getBeers(pageStart, perPage, direction)

            refresh_layout.isRefreshing = false
        }, Constant.REFRESH_DELAY)
    }
}
