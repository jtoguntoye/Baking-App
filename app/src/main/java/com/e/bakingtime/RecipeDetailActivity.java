package com.e.bakingtime;

import android.content.Intent;
import android.os.Bundle;

import com.e.bakingtime.Model.RecipeIngredients;
import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.Model.BakingSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepAdapter.StepClickHandler{

    public static final String PARCELED_RECIPE ="com.e.bakingtime.PARCELED_RECIPE";
    //create a recipe object to hold the recipe passed from the mainActivity
    private Recipes recipe;
    private ArrayList<BakingSteps> recipeSteps;

    private List<RecipeIngredients> recipeIngredients;
    private ArrayList<Object> bakingObject;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
