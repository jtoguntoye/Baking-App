package com.e.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipes implements Parcelable {




    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String recipeName;

    @SerializedName("ingredients")
    public List<RecipeIngredients> ingredients =new ArrayList<>();


    @SerializedName("steps")
    public List<BakingSteps> steps = new ArrayList<>();

    @SerializedName("servings")
    public int servings;

    @SerializedName("image")
    public String imageUrl;

    public Recipes(int recipeId, String name, List<RecipeIngredients> ingredients, List<BakingSteps> steps,
                  int servings, String image) {
        id = recipeId;
        recipeName = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        imageUrl = image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<RecipeIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<BakingSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<BakingSteps> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    //this inner class  helps to make the Recipes class re-creatable from a parcel
    public final static  Parcelable.Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel parcel) {
            return new Recipes(parcel);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };




    protected Recipes(Parcel parcel){
        id = parcel.readInt();
        recipeName = parcel.readString();
        parcel.readList(this.ingredients, RecipeIngredients.class.getClassLoader());
        parcel.readList(this.steps, BakingSteps.class.getClassLoader());
        servings = parcel.readInt();
        imageUrl = parcel.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(recipeName);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(imageUrl);
    }
}
