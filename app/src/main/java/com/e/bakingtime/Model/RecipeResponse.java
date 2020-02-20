package com.e.bakingtime.Model;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {


    @SerializedName("JSON")
    public List<Recipes> RecipeList;



    public List<Recipes> getRecipeList() {
        return RecipeList;
    }

    public void setRecipeList(List<Recipes> recipeList) {
        RecipeList = recipeList;
    }
}
