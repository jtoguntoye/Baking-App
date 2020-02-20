package com.e.bakingtime;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.e.bakingtime.Model.Recipes;

import java.util.List;

public class MainActivityModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;

    public MainActivityModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository();
    }

    public LiveData<List<Recipes>> getRecipes(){
        return recipeRepository.getRecipes();
    }
}
