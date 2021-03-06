package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.base.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.base.Constants
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.view.beerdetail.BeerDetailActivity
import kotlinx.android.synthetic.main.activity_beers_view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

internal class BeersViewActivity : BaseActivity<BeersViewModel.ViewModel>(), SwipyRefreshLayout.OnRefreshListener, BeersAdapter.ItemClickListener {

    @Inject lateinit var beersAdapter: BeersAdapter

    private val handler : Handler = Handler(Looper.getMainLooper())

    private var pageStart = 2
    private var perPage = 25

    override fun getLayoutRes(): Int = R.layout.activity_beers_view

    override fun getViewModel(): Class<BeersViewModel.ViewModel> = BeersViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe()

        initializeViews()
    }

    private fun subscribe() {
        viewModel.outpus.fetchBeers().observe { beers ->
            beersAdapter.addItems(beers)
        }

        viewModel.outpus.fetchIndex().observe { index ->
            pageStart = index
        }

        viewModel.outpus.isLoading().observe { isLoading ->
            if(isLoading) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }
    }

    private fun initializeViews() {
        setSupportActionBar(app_bar)

        with(beer_recyler) {
            adapter = this@BeersViewActivity.beersAdapter
            layoutManager = LinearLayoutManager(this@BeersViewActivity)
        }

        refresh_layout.setOnRefreshListener(this)
    }

    override fun onItemClick(beer: BeerModel) {
        startActivity<BeerDetailActivity>(Constants.KEY_BEAR_ID to beer.id)
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection) {
        handler.postDelayed({
            viewModel.intpus.fetchBeers(pageStart++, perPage, direction)

            refresh_layout.isRefreshing = false
        }, Constants.REFRESH_DELAY)
    }
}
