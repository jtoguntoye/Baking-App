package com.e.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.Utils.EspressoIdlingResource;
import com.e.bakingtime.Utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.RecipeClickHandler {

    private List<Recipes> mRecipesList;
    private RecyclerView mRecyclerView;
    private MainActivityAdapter mainActivityAdapter;
    private MainActivityModel mainActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecipesList = new ArrayList<>();
        mainActivityAdapter = new MainActivityAdapter(new ArrayList<>(), this);
        mRecyclerView = findViewById(R.id.recipe_recycler);


        GridLayoutManager gridLayoutManager =new GridLayoutManager(this, numOfColumns());
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mainActivityAdapter);

        mainActivityModel = new MainActivityModel(this.getApplication());


        getRecipes();

    }



    private void getRecipes(){

        EspressoIdlingResource.increment();
        mainActivityModel.getRecipes().observe(this, (List<Recipes> recipesList) -> {
            mainActivityAdapter.setAdapter(recipesList);
            mRecipesList = recipesList;
            Log.d("Recipe size:", String.valueOf((mRecipesList.size())));

        });
        EspressoIdlingResource.decrement();
    }

    private int numOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 700;
        int width = displayMetrics.widthPixels;
        int numColumns = width/widthDivider;
        if(numColumns < 2)return 1;
        return numColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // recipe itemClick callback method implementation
    @Override
    public void onRecipeClickHandler(int position) {
        Intent recipeClickIntent = new Intent(this, RecipeDetailActivity.class);
        recipeClickIntent.putExtra(RecipeDetailActivity.PARCELED_RECIPE, mRecipesList.get(position));

        updateSharedPreference(mRecipesList.get(position));
        sendBroadcastToWidget();
        startActivity(recipeClickIntent);
    }


    private void sendBroadcastToWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));

        Intent updateAppWidgetIntent = new Intent();
        updateAppWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateAppWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(updateAppWidgetIntent);
    }


    private void updateSharedPreference(Recipes recipe) {
        // Get a instance of SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Get the editor object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the ingredient list and convert the list to string
        String ingredientString = Utils.toIngredientString(recipe.getIngredients());

        // Save the string used for displaying in the app widget
        editor.putString(getString(R.string.pref_ingredient_list_key), ingredientString);
        editor.putString(getString(R.string.pref_recipe_name_key), recipe.getRecipeName());

        // Convert the list of the steps to String
        String stepString = Utils.toStepString(recipe.getSteps());

        // Save the recipe data used for launching the DetailActivity
        editor.putInt(getString(R.string.pref_recipe_id_key), recipe.getId());
        editor.putString(getString(R.string.pref_step_list_key), stepString);
        editor.putString(getString(R.string.pref_image_key), recipe.getImageUrl());
        editor.putInt(getString(R.string.pref_servings_key), recipe.getServings());

        editor.apply();
    }

}
