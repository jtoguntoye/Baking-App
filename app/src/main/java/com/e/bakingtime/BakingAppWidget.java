package com.e.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.e.bakingtime.Model.BakingSteps;
import com.e.bakingtime.Model.RecipeIngredients;
import com.e.bakingtime.Model.Recipes;
import com.e.bakingtime.RemoteViews.IngredientsRemoteViewService;

import java.util.List;
import java.util.prefs.Preferences;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    /** The default value for the SharedPreferences */
    public static final String DEFAULT_STRING = "";
    public static final int DEFAULT_INTEGER = 1;
    public static final int DEFAULT_INTEGER_FOR_SERVINGS = 8;
    /** Extra for the recipe to be received in the intent */
    public static final String EXTRA_RECIPE = "recipe";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the recipe name used for displaying in the app widget
        String recipeName = sharedPreferences.getString(
                context.getString(R.string.pref_recipe_name_key), DEFAULT_STRING);

        // Extract recipe data used for creating the recipe object from sharedPreferences
        int recipeId = sharedPreferences.getInt(
                context.getString(R.string.pref_recipe_id_key), DEFAULT_INTEGER);
        String ingredientString = sharedPreferences.getString(
                context.getString(R.string.pref_ingredient_list_key),  DEFAULT_STRING);
        String stepString = sharedPreferences.getString(
                context.getString(R.string.pref_step_list_key),  DEFAULT_STRING);
        int servings =  sharedPreferences.getInt(
                context.getString(R.string.pref_servings_key), DEFAULT_INTEGER_FOR_SERVINGS);
        String image = sharedPreferences.getString(
                context.getString(R.string.pref_image_key),  DEFAULT_STRING);



        RemoteViews views = new RemoteViews(
                context.getPackageName(),
                R.layout.baking_app_widget
        );
         Intent intent;

        views.setTextViewText(R.id.widgetTitleLabel,recipeName);
        // click event handler for the title, launches the app when the user clicks on title
        if (ingredientString.isEmpty() || stepString.isEmpty()) {
            intent = new Intent(context, MainActivity.class);
            // Display the app name in the app widget
            views.setTextViewText(R.id.widgetTitleLabel, context.getString(R.string.app_name));
        }
            else {
                intent = new Intent(context, RecipeDetailActivity.class);

                // Convert ingredient string to the list of ingredients
                List<RecipeIngredients> ingredientList = Utils.toIngredientList(ingredientString);
                // Convert step string to the list of steps
                List<BakingSteps> stepList = Utils.toStepList(stepString);

                // Create the recipe that a user clicks
                Recipes recipe = new Recipes(recipeId, recipeName, ingredientList, stepList, servings, image);

                // Pass the bundle through Intent
                Bundle b = new Bundle();
                b.putParcelable(EXTRA_RECIPE, recipe);
                intent.putExtra(EXTRA_RECIPE, b);

                // Display the recipe name in the app widget
                views.setTextViewText(R.id.widgetTitleLabel, recipeName);
            }
        // Widgets allow click handlers to only launch pending intents
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetTitleLabel, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.baking_app_widget
            );

            Intent ServiceIntent = new Intent(context, IngredientsRemoteViewService.class);
            ServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setRemoteAdapter(R.id.widgetListView, ServiceIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

            updateAppWidget(context,appWidgetManager,appWidgetId);
            // Trigger data update to handle the ListView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
            // Trigger data update to handle the ListView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

