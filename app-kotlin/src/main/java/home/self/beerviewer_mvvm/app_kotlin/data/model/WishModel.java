package home.self.beerviewer_mvvm.app_kotlin.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Tak on 2018. 1. 28..
 */

@Entity(tableName = "wish", foreignKeys = ForeignKey(entity = BeerModel::class, parentColumns = arrayOf("id"), childColumns = arrayOf("beer_id")))
class WishModel(@field:ColumnInfo(name = "beer_id")
                var beerId: Long, @field:ColumnInfo(name = "wish")
                var isWish: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

















