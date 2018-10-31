package home.self.beerviewer_mvvm.app_kotlin.view.beersview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import home.self.beerviewer_mvvm.app_kotlin.R
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import kotlinx.android.synthetic.main.beer_item.view.*
import java.util.*

/**
 * Created by Tak on 2018. 1. 27..
 */

class BeersAdapter : RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    private val items = ArrayList<BeerModel>()

    private var listener : ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BeersViewHolder(parent)

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        items[position].let { beer ->
            with(holder.itemView) {
                Glide.with(context)
                        .load(beer.imageUrl)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(beer_img)
                beer_title.text = beer.name
                beer_id.text = beer.id.toString()
                beer_tagline.text = beer.tagline
                beer_first_brewed.text = beer.firstBrewed

                setOnClickListener { listener?.onItemClick(beer) }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(beers: List<BeerModel>) {
        var addLocation = 0
        if (beers[0].direction == SwipyRefreshLayoutDirection.TOP)
            addLocation = 0
        else if (beers[0].direction == SwipyRefreshLayoutDirection.BOTTOM)
            addLocation = items.size

        this.items.addAll(addLocation, beers)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener : ItemClickListener?) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onItemClick(beer : BeerModel)
    }

    class BeersViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.beer_item, parent, false))
}
