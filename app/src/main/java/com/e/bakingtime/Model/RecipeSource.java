package com.e.bakingtime.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.bakingtime.Model.RecipeResponse;
import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.Retrofit.RecipeInterface;
import com.e.bakingtime.Retrofit.RetrofitInstance;

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

        mRecipeInterface.getRecipes().enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.body()!=null){
                    recipeList.postValue(response.body().getRecipeList());

                    Log.d("DATASOURCE", "SIZE:"+ response.body().getRecipeList().size());
                }
                else { Log.d("DATASOURCE:", "Response is null");}
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
            t.printStackTrace();
            }
        });

        return recipeList;
    }

}
