package com.e.bakingtime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.bakingtime.Model.Ingredients;
import com.e.bakingtime.Model.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends Fragment {

    private List<Ingredients> ingredientsList;
    private List<Steps> stepList;



    //mandatory constructor for instantiating the fragment
    public RecipeDetailFragment (){ }


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ingredientsView = inflater.inflate(R.layout.fragment_recipe_detail,container,false);

        RecyclerView ingredientsRecyclerView = ingredientsView.findViewById(R.id.ingredients_recycler);
        RecyclerView stepsRecyclerView = ingredientsView.findViewById(R.id.steps_recycler);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
         ingredientsAdapter.setIngredientList(ingredientsList);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);


        StepsAdapter stepsAdapter = new StepsAdapter(new ArrayList<>());
        stepsAdapter.setStepsList(stepList);
        stepsRecyclerView.setAdapter(stepsAdapter);

    return ingredientsView;
    }
    /*helper method to set the data for the ingredients recycler views.
    This method is called by the fragment's host activity to pass data to the fragment
    * */
    public void setIngredientsList(List<Ingredients> ingredientsList){
        this.ingredientsList = ingredientsList;

    }

    public void SetBakingStepsList(List<Steps> stepList1){
        this.stepList = stepList1;
    }
}
