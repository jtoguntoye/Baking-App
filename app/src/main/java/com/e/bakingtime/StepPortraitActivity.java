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
    private  Button previousButton, nextButton;
    private  int currentPosition;
    private ViewStepFragment viewStepFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_view_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewStepFragment = new ViewStepFragment();

        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);

        stepsList = getIntent().getParcelableArrayListExtra(StepPortraitActivity.PARCELED_LIST);
        Log.d("StepList size:", String.valueOf(stepsList.size()));
        step = getIntent().getParcelableExtra(StepPortraitActivity.PARCELED_STEP);

        if(stepsList!=null){
            currentPosition = getIndexOfStep(step);
            Log.d("Position", String.valueOf(currentPosition));
        }

        recipeName=getIntent().getStringExtra(RECIPE_NAME);
        if(recipeName !=null){
        setTitle(recipeName);
        }

        if(savedInstanceState ==null){
            Bundle  arguments = new Bundle();

            //pass the intent extras to the viewStepFragment
            arguments.putParcelable(PARCELED_STEP,getIntent().getParcelableExtra(PARCELED_STEP));

            viewStepFragment.setArguments(arguments);
            performFragmentTransaction(viewStepFragment);


        }

        setButtonClickListener();



    }


    private  void setButtonClickListener() {

        previousButton.setOnClickListener(new View.OnClickListener() {
            ViewStepFragment mviewStepFragment = new ViewStepFragment();
            Bundle bundle = new Bundle();
            @Override
            public void onClick(View view) {
                if(currentPosition == 0) {
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(stepsList.size() - 1));
                    mviewStepFragment.setArguments(bundle);
                    performFragmentTransaction(mviewStepFragment);

                }
                else{
                    bundle.putParcelable(StepPortraitActivity.PARCELED_STEP,
                            stepsList.get(currentPosition - 1));
                    mviewStepFragment.setArguments(bundle);
                    --currentPosition;
                    performFragmentTransaction(mviewStepFragment);


                }

            }
        });

    }

    public void performFragmentTransaction(ViewStepFragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, fragment)
                .commit();

    }

    private int getIndexOfStep(BakingSteps step) {
        for(BakingSteps bakingStep : stepsList)  {
            if(bakingStep.getId().equals(step.getId()))
                return stepsList.indexOf(bakingStep);
        }
        return -1;
    }
}