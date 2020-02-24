package com.e.bakingtime;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.Retrofit.RecipeInterface;
import com.e.bakingtime.Retrofit.RetrofitInstance;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSource {

private MutableLiveData<List<Recipes>> recipeList;
private RecipeInterface mRecipeInterface;


public RecipeSource(){
        recipeList = new MutableLiveData<>();
        mRecipeInterface = RetrofitInstance.getInstance();
    }

    public LiveData<List<Recipes>> getRecipeList(){

        mRecipeInterface.getRecipes().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.body()!= null){
                try {
                    String recipeString = response.body().toString();

                    //create a TypeToken to help deserialize the json
                    Type ListType = new TypeToken<List<Recipes>>(){}.getType();
                    recipeList.postValue(getRecipeListFromJson(recipeString,ListType));
                }
                catch (Exception e){
                    e.printStackTrace();;
                }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

        return recipeList;
    }


    //helper method to
    public static <T> List<T> getRecipeListFromJson(String jsonString, Type type) {
        if (!isValid(jsonString)) {
            return null;
        }
        return new Gson().fromJson(jsonString, type);
    }

    public static boolean isValid(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException jse) {
            return false;
        }
    }
}


