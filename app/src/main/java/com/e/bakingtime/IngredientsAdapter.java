package com.e.bakingtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.bakingtime.Model.Ingredients;

import java.util.List;

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredients> ingredientList;

    public IngredientsAdapter(List<Ingredients> ingredientList) {
        this.ingredientList = ingredientList;
    }

    void setIngredientList(List<Ingredients> ingredientList){
        this.ingredientList = ingredientList;
    }


    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_ingredient,parent, false);

        return new IngredientsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {

    holder.bind(ingredientList.get(position));

    }


    @Override
    public int getItemCount() {

        return ingredientList==null?0:ingredientList.size();
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder{

        private final TextView ingredientName;

        private IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredients_text);
        }

        private void bind(Ingredients ingredients){
            ingredientName.setText(ingredients.getIngredientName());
        }
    }

}
