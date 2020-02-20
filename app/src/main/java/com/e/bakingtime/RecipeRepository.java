package com.e.bakingtime;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.e.bakingtime.Model.RecipeSource;
import com.e.bakingtime.Model.Recipes;

import java.util.List;

public class RecipeRepository {

private  final RecipeSource recipeSource;

    public RecipeRepository() { recipeSource = new RecipeSource(); }

    public LiveData<List<Recipes>> getRecipes(){

      //  Log.d("Repository recipe:","size is"+recipeSource.getRecipeList().getValue().size());
        return recipeSource.getRecipeList();
    }

}
