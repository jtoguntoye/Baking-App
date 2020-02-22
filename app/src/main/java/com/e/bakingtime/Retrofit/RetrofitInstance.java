package com.e.bakingtime.Retrofit;

import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/";
    private static  Retrofit retrofitInstance = null;

    private RetrofitInstance() { }

    public static RecipeInterface getInstance(){
        if(retrofitInstance == null){
        retrofitInstance = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
return retrofitInstance.create(RecipeInterface.class);
    }

}
