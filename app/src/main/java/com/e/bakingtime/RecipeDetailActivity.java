package com.e.bakingtime;

import android.content.Intent;
import android.os.Bundle;

import com.e.bakingtime.Model.Recipes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.View;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String PARCELED_RECIPE ="com.e.bakingtime.PARCELED_RECIPE";
    //create a recipe object to hold the recipe passed from the mainActivity
    private Recipes recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

        //use a fragment manager to add fragments to the activity screen
        FragmentManager fragmentManager = getSupportFragmentManager();

        //fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container,recipeDetailFragment)
                .commit();


        Intent getRecipeIntent = getIntent();
        recipe =getRecipeIntent.getParcelableExtra(PARCELED_RECIPE);

        if(recipe!= null){
            recipeDetailFragment.setIngredientsList(recipe.getIngredients());
            recipeDetailFragment.SetBakingStepsList(recipe.getSteps());
        }

    }

}
