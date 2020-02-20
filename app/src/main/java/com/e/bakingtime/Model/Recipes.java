package com.e.bakingtime.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.SkipCallbackExecutor;

public class Recipes {


    @SerializedName("id")
    public int id;

    @SerializedName("Name")
    public String recipeName;

    @SerializedName("ingredients")
    public List<Ingredients> ingredients;


    @SerializedName("steps")
    public List<Steps> steps;

    @SerializedName("servings")
    public int servings;

    @SerializedName("image")
    public String imageUrl;



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

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
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

}
