package home.self.beerviewer_mvvm.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class Ingredients {
    @SerializedName("malt")
    @Ignore
    List<Malt> malts;

    @SerializedName("hops")
    @Ignore
    List<Hops> hops;

    @SerializedName("yeast")
    @ColumnInfo(name = "yeast")
    String yeast;

    public Ingredients(){}
    public Ingredients(List<Malt> malts, List<Hops> hops, String yeast) {
        this.malts = malts;
        this.hops = hops;
        this.yeast = yeast;
    }

    public List<Malt> getMalts() {
        return malts;
    }

    public void setMalts(List<Malt> malts) {
        this.malts = malts;
    }

    public List<Hops> getHops() {
        return hops;
    }

    public void setHops(List<Hops> hops) {
        this.hops = hops;
    }

    public String getYeast() {
        return yeast;
    }

    public void setYeast(String yeast) {
        this.yeast = yeast;
    }
}
