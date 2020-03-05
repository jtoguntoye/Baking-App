package com.e.bakingtime;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.e.bakingtime.Model.BakingSteps;

import java.util.ArrayList;
import java.util.Objects;

public class StepPortraitActivity extends AppCompatActivity {

    public static final String PARCELED_STEP = "com.e.bakingtime.PARCELED_RECIPE";
    public static final String RECIPE_NAME = "com.e.bakingtime.RECIPE_NAME";
    public static final String PARCELED_LIST = "com.e.bakingtime.PARCELED_LIST";
    private BakingSteps step;
    private ArrayList<BakingSteps> stepsList;
    private String recipeName;
    private ViewStepFragment viewStepFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_view_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewStepFragment = new ViewStepFragment();



        stepsList = getIntent().getParcelableArrayListExtra(StepPortraitActivity.PARCELED_LIST);
        Log.d("StepList size:", String.valueOf(stepsList.size()));
        step = getIntent().getParcelableExtra(StepPortraitActivity.PARCELED_STEP);


        recipeName=getIntent().getStringExtra(RECIPE_NAME);
        if(recipeName !=null){
        setTitle(recipeName);
        }

        if(savedInstanceState ==null){
            Bundle  arguments = new Bundle();

            //pass the intent extras to the viewStepFragment
            arguments.putParcelable(PARCELED_STEP,step);
            arguments.putParcelableArrayList(StepPortraitActivity.PARCELED_LIST,
                    stepsList);

            viewStepFragment.setArguments(arguments);
            performFragmentTransaction(viewStepFragment);


        }




    }

    public void performFragmentTransaction(ViewStepFragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();

    }


}