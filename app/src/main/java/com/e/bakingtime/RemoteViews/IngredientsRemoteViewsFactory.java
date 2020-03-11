package com.e.bakingtime.RemoteViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;

import com.e.bakingtime.MainActivityModel;
import com.e.bakingtime.Model.RecipeIngredients;
import com.e.bakingtime.R;
import com.e.bakingtime.RecipeRepository;
import com.e.bakingtime.Utils;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<RecipeIngredients> recipeIngredientsList;


    public IngredientsRemoteViewsFactory(Context context) {
        this.context = context;
        recipeIngredientsList = new ArrayList<>();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        //get the data to fill the widget here
        // Get the updated ingredient string from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String RecipeName = sharedPreferences.getString(context.getString(R.string.pref_recipe_name_key),"");
        String ingredientString = sharedPreferences.getString
                (context.getString(R.string.pref_ingredient_list_key), "");

        // Convert ingredient string to the list of ingredients
        recipeIngredientsList = Utils.toIngredientList(ingredientString);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeIngredientsList== null ? 0 : recipeIngredientsList.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (recipeIngredientsList== null || recipeIngredientsList.size()==0) {
            return null;
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_list_item);
        rv.setTextViewText(R.id.widgetItemNameLabel, recipeIngredientsList.get(position).getIngredientName());

    return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
