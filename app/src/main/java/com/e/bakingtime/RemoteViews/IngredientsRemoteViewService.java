package com.e.bakingtime.RemoteViews;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.e.bakingtime.Model.RecipeIngredients;
import com.e.bakingtime.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRemoteViewService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new IngredientsRemoteViewsFactory(this.getApplicationContext());

    }


    }


