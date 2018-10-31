package home.self.beerviewer_mvvm.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Tak on 2018. 1. 27..
 */

@Entity(tableName = "beer")
public class BeerModel {

    @SerializedName("id")
    @PrimaryKey
    public int id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    String name;

    @SerializedName("tagline")
    @ColumnInfo(name = "tagline")
    String tagline;

    @SerializedName("first_brewed")
    @ColumnInfo(name = "first_brewed")
    String firstBrewed;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    String description;

    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    String imageUrl;

    @SerializedName("ingredients")
    @Embedded
    Ingredients ingredients;

    @SerializedName("food_pairing")
    @Ignore
    List<String> foodPairings;

    @SerializedName("brewers_tips")
    @ColumnInfo(name = "brewers_tips")
    String brewersTips;

    @SerializedName("contributed_by")
    @ColumnInfo(name = "contributed_by")
    String contributedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed = firstBrewed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getFoodPairings() {
        return foodPairings;
    }

    public void setFoodPairings(List<String> foodPairings) {
        this.foodPairings = foodPairings;
    }

    public String getBrewersTips() {
        return brewersTips;
    }

    public void setBrewersTips(String brewersTips) {
        this.brewersTips = brewersTips;
    }

    public String getContributedBy() {
        return contributedBy;
    }

    public void setContributedBy(String contributedBy) {
        this.contributedBy = contributedBy;
    }
}


















