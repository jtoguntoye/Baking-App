package com.e.bakingtime.Retrofit;

import com.e.bakingtime.Model.RecipeResponse;
import com.e.bakingtime.Model.Recipes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<RecipeResponse> getRecipes();
}
