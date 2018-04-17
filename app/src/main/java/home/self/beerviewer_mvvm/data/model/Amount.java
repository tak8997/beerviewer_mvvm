package home.self.beerviewer_mvvm.data.model;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tak on 2018. 1. 27..
 */

public class Amount {
    @SerializedName("value")
    @ColumnInfo(name = "value")
    double value;

    @SerializedName("unit")
    @ColumnInfo(name = "unit")
    String unit;

    public Amount(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
