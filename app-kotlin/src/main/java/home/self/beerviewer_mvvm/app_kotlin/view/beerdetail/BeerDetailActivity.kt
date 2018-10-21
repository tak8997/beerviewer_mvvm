package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import home.self.beerviewer_mvvm.app_kotlin.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.rx.lifecycle.AutoClearedDisposable
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import kotlinx.android.synthetic.main.activity_beer_detail.*
import org.jetbrains.anko.longToast
import javax.inject.Inject


internal class BeerDetailActivity : BaseActivity<BeerDetailViewModel.ViewModel>() {

    private var beerInfo: String? = null

    val disposables = AutoClearedDisposable(this)
    val viewDisposables = AutoClearedDisposable(this, false)

    override fun getLayoutRes(): Int = R.layout.activity_beer_detail

    override fun getViewModel(): Class<BeerDetailViewModel.ViewModel> = BeerDetailViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDisposables.add(viewModel.beer
                .observeOn(schedulerProvider.ui())
                .subscribe { beer ->
                    beerInfo = (beer.name + "\n" + beer.tagline + "\n" + beer.description + "\n"
                            + beer.brewersTips + "\n" + beer.contributedBy + "\n" + beer.firstBrewed)

                    Glide.with(this)
                            .load(beer.imageUrl)
                            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(beer_img)
                    beer_title.text = beer.name
                    beer_tagline.text = beer.tagline
                    beer_description.text = beer.description
                    beer_brewers_tips.text = beer.brewersTips
                    beer_contributed_by.text = beer.contributedBy
                    beer_first_brewed.text = beer.firstBrewed
                    app_bar_title.text = beer.name
                })

        viewDisposables.add(viewModel.message
                .observeOn(schedulerProvider.ui())
                .subscribe { msg -> longToast(msg) })

        viewDisposables.add(viewModel.isLoading
                .observeOn(schedulerProvider.ui())
                .subscribe { isLoading -> })

        viewDisposables.add(viewModel.beerInfo
                .observeOn(schedulerProvider.ui())
                .subscribe { beerInfo ->
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, beerInfo)
                    intent.type = "text/plain"
                    startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
                })

        disposables.add(viewModel.getBeer())

        setSupportActionBar(app_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                beerInfo?.let { viewModel.beerInfo.onNext(it) }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
