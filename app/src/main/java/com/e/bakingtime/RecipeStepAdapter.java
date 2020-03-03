package com.e.bakingtime;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.bakingtime.Model.BakingSteps;
import com.e.bakingtime.Model.RecipeIngredients;

import java.util.ArrayList;
import java.util.List;

class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> dataSet;
    private static final int INGREDIENT = 0;
    private static final int STEP =1;
    public boolean isTwoPane;

    private StepClickHandler mStepClickHandler;

    public RecipeStepAdapter(List<Object> bakingObject, boolean mTwoPane, StepClickHandler stepClickHandler) {
        dataSet = bakingObject;
        this.isTwoPane = mTwoPane;
        this.mStepClickHandler = stepClickHandler;
        Log.d("dataSetsize", String.valueOf(dataSet.size()));

    }

    @Override
    public int getItemViewType(int position) {
        if(dataSet.get(position) instanceof RecipeIngredients){
            return INGREDIENT;
        }
        else if(dataSet.get(position) instanceof BakingSteps){
            return STEP;
        }
        return -1;
     }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case INGREDIENT:
                View ingredientView = inflater.inflate(R.layout.item_recipe_ingredient, parent, false);
                viewHolder = new IngredientsViewHolder(ingredientView);
                break;
            case STEP:
                View  stepView = inflater.inflate(R.layout.item_baking_steps,parent,false);
                viewHolder = new stepsViewHolder(stepView);
                break;
            default:
                View view = inflater.inflate(R.layout.item_baking_steps,parent,false);
                viewHolder = new stepsViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    switch (holder.getItemViewType()){
        case INGREDIENT:
            IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
            ingredientsViewHolder.bind((RecipeIngredients)dataSet.get(position));
        break;
        case STEP:
            stepsViewHolder stepsViewHolder = (stepsViewHolder) holder;
            stepsViewHolder.bind((BakingSteps) dataSet.get(position));
        break;
        default:
            stepsViewHolder stepsViewHolder1 = (stepsViewHolder) holder;
            stepsViewHolder1.bind((BakingSteps) dataSet.get(position)) ;
    }

    }


    @Override
    public int getItemCount() {
        return dataSet==null ?0 : dataSet.size();
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder{

        private final TextView ingredientName;

        private IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredients_text);
        }


        private void bind(RecipeIngredients recipeIngredients){
            if(recipeIngredients!= null){
            ingredientName.setText(recipeIngredients.getIngredientName());
        }
        }
    }

    public class stepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView stepsText;

        public stepsViewHolder(@NonNull View itemView) {
            super(itemView);

            stepsText = itemView.findViewById(R.id.steps_text);

            itemView.setOnClickListener(this);
        }

        private void bind(BakingSteps step) {
            stepsText.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            mStepClickHandler.onStepClickHandler(getAdapterPosition());
        }
    }

    public interface StepClickHandler {
        void onStepClickHandler(int position);
    }

}
