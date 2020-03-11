package com.e.bakingtime;

import com.e.bakingtime.Model.BakingSteps;
import com.e.bakingtime.Model.RecipeIngredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Utils {



    /**
     * Convert ingredient string to the list of ingredients
     *
     */
    public static List<RecipeIngredients> toIngredientList(String ingredientString) {
        if (ingredientString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<RecipeIngredients>>() {}.getType();
        return gson.fromJson(ingredientString, listType);
    }

    /**
     * Convert the list of ingredients to the String.
     */
    public static String toIngredientString(List<RecipeIngredients> ingredientList) {
        if (ingredientList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<RecipeIngredients>>() {}.getType();
        return gson.toJson(ingredientList, listType);
    }

    /**
     * Convert the String to the list of steps.
     */
    public static List<BakingSteps> toStepList(String stepString) {
        if (stepString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type stepListType = new TypeToken<List<BakingSteps>>() {}.getType();
        return gson.fromJson(stepString, stepListType);
    }

    /**
     * Convert the list of steps to the String.
     */
    public static String toStepString(List<BakingSteps> stepList) {
        if (stepList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type stepListType = new TypeToken<List<BakingSteps>>() {}.getType();
        return gson.toJson(stepList, stepListType);
    }
}
