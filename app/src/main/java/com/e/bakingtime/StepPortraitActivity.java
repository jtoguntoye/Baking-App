package com.e.bakingtime;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.e.bakingtime.Model.BakingSteps;

public class StepPortraitActivity extends AppCompatActivity {

    public static final String PARCELED_STEP = "com.e.bakingtime.PARCELED_RECIPE";
    public static final String RECIPE_NAME = "com.e.bakingtime.RECIPE_NAME";
    private BakingSteps step;
    private String recipeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_view_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewStepFragment viewStepFragment = new ViewStepFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_container, viewStepFragment)
                .commit();

        Intent getStepIntent = getIntent();
        step = getStepIntent.getParcelableExtra(PARCELED_STEP);
        recipeName = getStepIntent.getStringExtra(RECIPE_NAME);

        if(recipeName !=null) {
            this.setTitle(recipeName);
        }
        if(step != null){
            viewStepFragment.setLongDescription(step.getDescription());
            viewStepFragment.setVideoUrlString(step.getVideoURL());
        }
    }
}