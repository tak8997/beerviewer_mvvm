package home.self.beerviewer_mvvm.app_kotlin.view.beerdetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import home.self.beerviewer_mvvm.app_kotlin.base.BaseActivity
import home.self.beerviewer_mvvm.app_kotlin.base.Parameter
import home.self.beerviewer_mvvm.app_kotlin.R
import kotlinx.android.synthetic.main.activity_beer_detail.*
import org.jetbrains.anko.toast


internal class BeerDetailActivity : BaseActivity<BeerDetailViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_beer_detail

    override fun getViewModel(): Class<BeerDetailViewModel.ViewModel> = BeerDetailViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe()

        setSupportActionBar(app_bar)
    }

    private fun subscribe() {
        viewModel.outputs.fetchBeer().observe { beer ->
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
        }

        viewModel.outputs.fetchBeerInfo().observe { beerInfo ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"

                putExtra(Intent.EXTRA_TEXT, beerInfo)
            }

            startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
        }

        viewModel.outputs.message().observe { message ->
            toast(message)
        }

        viewModel.outputs.isLoading().observe { isLoading ->
            if(isLoading) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                viewModel.inputs.clickBeerInfo(Parameter.CLICK)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
