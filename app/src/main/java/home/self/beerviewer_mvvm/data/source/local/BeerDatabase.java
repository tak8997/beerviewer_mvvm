package home.self.beerviewer_mvvm.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import java.util.List;

import home.self.beerviewer_mvvm.BeerViewerApplication;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.model.WishModel;


/**
 * Created by Tak on 2018. 1. 27..
 */

@Database(
        entities = {
                BeerModel.class,
                WishModel.class
        },
        version = 2
)
public abstract class BeerDatabase extends RoomDatabase {

    private static final String DB_NAME = "beer_db";
    private static BeerDatabase instance;

    public static BeerDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    BeerViewerApplication.getInstance(),
                    BeerDatabase.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract BeerDao beerDao();

    public void addBeers(List<BeerModel> beers) {
        List<BeerModel> previous = beerDao().getAllBeers();
        List<BeerModel> inserts = beers;

        beerDao().deleteBeers(previous);
        beerDao().insertBeers(inserts);
    }

    public void insertOrUpdateWish(WishModel wish) {
        beerDao().insertWish(wish);
    }
}
















