package com.e.bakingtime.Retrofit;



import com.google.gson.JsonArray;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<JsonArray> getRecipes();
}
