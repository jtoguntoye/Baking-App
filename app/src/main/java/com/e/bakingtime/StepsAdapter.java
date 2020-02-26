package com.e.bakingtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.bakingtime.Model.Ingredients;
import com.e.bakingtime.Model.Steps;

import java.util.List;
import java.util.zip.Inflater;

class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.stepsViewHolder>  {

    private List<Steps> stepsList;


   public StepsAdapter(List<Steps> stepsList1){
        this.stepsList = stepsList1;
    }


    public void setStepsList(List<Steps>stepsLists) {
        this.stepsList = stepsLists;
    }



    @NonNull
    @Override
    public stepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_baking_steps,parent,false);
        return new stepsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull stepsViewHolder holder, int position) {
       holder.bind(stepsList.get(position));

    }

    @Override
    public int getItemCount() {
        return stepsList ==null ?0 :stepsList.size();
    }

    public class stepsViewHolder extends RecyclerView.ViewHolder{

        private final TextView stepsText;

        public stepsViewHolder(@NonNull View itemView) {
            super(itemView);

            stepsText= itemView.findViewById(R.id.steps_text);
        }

        private void bind(Steps step){
            stepsText.setText(step.getShortDescription());
        }

    }
}
