package com.e.bakingtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.bakingtime.Model.Recipes;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.RecipeViewHolder> {

    private TextView recipeTextView;
     private List<Recipes> recipesList;
     private RecipeClickHandler mRecipeClickHandler;

      MainActivityAdapter(List<Recipes> recipesList, RecipeClickHandler recipeClickHandler){
         this.recipesList = recipesList;
         mRecipeClickHandler = recipeClickHandler;
    }

    void setAdapter(List<Recipes> recipesList1){
         this.recipesList = recipesList1;
         notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MainActivityAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
          holder.bind(recipesList.get(position));

    }

    @Override
    public int getItemCount() {
        return recipesList == null?0:recipesList.size();
    }


    public  class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
        recipeTextView = itemView.findViewById(R.id.recipe_text_view);

        itemView.setOnClickListener(this);

        }

        public void bind(Recipes recipes) {
            recipeTextView.setText(recipes.getRecipeName());
        }

        @Override
        public void onClick(View view) {
            mRecipeClickHandler.onRecipeClickHandler(getAdapterPosition());

        }
    }

    //create a click listener to handle item clickon the recycler view items
        public  interface RecipeClickHandler {
          void onRecipeClickHandler(int position);
    }
}
