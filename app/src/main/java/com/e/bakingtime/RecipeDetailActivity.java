package com.e.bakingtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.e.bakingtime.Model.RecipeIngredients;
import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.Model.BakingSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepAdapter.StepClickHandler{

    public static final String PARCELED_RECIPE ="com.e.bakingtime.PARCELED_RECIPE";
    //create a recipe object to hold the recipe passed from the mainActivity
    private Recipes recipe;
    private ArrayList<BakingSteps> recipeSteps;

    private List<RecipeIngredients> recipeIngredients;
    private ArrayList<Object> bakingObject;
    private boolean mTwoPane;
    private SharedPreferences mDesiredRecipePref;
    private  String DesiredPref ="com.e.bakingtime.desiredRecipeSharedPref;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mDesiredRecipePref = getSharedPreferences(DesiredPref, MODE_PRIVATE);

        bakingObject = new ArrayList<>();
        recipeSteps = new ArrayList<>();

        if(findViewById(R.id.item_detail_container)!=null){
            mTwoPane =true;
        }
        RecyclerView DetailRecyclerView = findViewById(R.id.item_list);

        assert DetailRecyclerView !=null;


        setUpRecyclerView(DetailRecyclerView);


        Intent getRecipeIntent = getIntent();
        recipe =getRecipeIntent.getParcelableExtra(PARCELED_RECIPE);


        if(recipe!= null){
            this.setTitle(recipe.getRecipeName());
            recipeIngredients = recipe.getIngredients();
            recipeSteps = (ArrayList<BakingSteps>) recipe.getSteps();
            bakingObject.addAll(recipeIngredients);
            bakingObject.addAll(recipeSteps);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.set_favorite_recipe, menu);
        return true;
    }




    private void setUpRecyclerView(RecyclerView detailRecyclerView) {
        detailRecyclerView.setHasFixedSize(true);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(bakingObject,mTwoPane,this);
        detailRecyclerView.setAdapter(recipeStepAdapter);
    }

    @Override
    public void onStepClickHandler(int position) {

        if(mTwoPane){
            BakingSteps ClickedStep = (BakingSteps) bakingObject.get(position);
            Bundle arguments = new Bundle();
            arguments.putBoolean("isTwopane",mTwoPane);
            arguments.putParcelable(StepPortraitActivity.PARCELED_STEP, ClickedStep);
            ViewStepFragment viewStepFragment = new ViewStepFragment();
            viewStepFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, viewStepFragment)
                    .commit();

        }else{
            BakingSteps clickedStep = (BakingSteps) bakingObject.get(position);

        Intent StepClickIntent = new Intent(this,StepPortraitActivity.class);
            StepClickIntent.putParcelableArrayListExtra(StepPortraitActivity.PARCELED_LIST, recipeSteps);
        StepClickIntent.putExtra(StepPortraitActivity.PARCELED_STEP, clickedStep);
        StepClickIntent.putExtra(StepPortraitActivity.RECIPE_NAME,recipe.getRecipeName());
        startActivity(StepClickIntent);
    }
    }
}
