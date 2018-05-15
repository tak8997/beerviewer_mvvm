package home.self.beerviewer_mvvm.app_kotlin.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase

import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import home.self.beerviewer_mvvm.app_kotlin.data.model.BeerModel
import home.self.beerviewer_mvvm.app_kotlin.data.model.WishModel


/**
 * Created by Tak on 2018. 1. 27..
 */

@Database(entities = arrayOf(BeerModel::class, WishModel::class), version = 2)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerDao

    fun addBeers(beers: List<BeerModel>) {
        val previous = beerDao().allBeers
        val inserts = beers

        beerDao().deleteBeers(previous)
        beerDao().insertBeers(inserts)
    }

    fun insertOrUpdateWish(wish: WishModel) {
        beerDao().insertWish(wish)
    }

    companion object {

        private val DB_NAME = "beer_db"
        private var instance: BeerDatabase? = null

        fun getInstance(): BeerDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        BeerViewerApplication.getInstance(),
                        BeerDatabase::class.java,
                        DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance
        }
    }
}
















