package com.e.bakingtime;

import android.os.Bundle;

import com.e.bakingtime.Model.Recipes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipes> mRecipesList;
    private RecyclerView mRecyclerView;
    private MainActivityAdapter mainActivityAdapter;
    private MainActivityModel mainActivityModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecipesList = new ArrayList<>();
        mainActivityAdapter = new MainActivityAdapter(new ArrayList<Recipes>());
        mRecyclerView = findViewById(R.id.recipe_recycler);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this, numOfColumns());
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mainActivityAdapter);

        mainActivityModel = new MainActivityModel(this.getApplication());

        getRecipes();

    }

    private void getRecipes(){

        mainActivityModel.getRecipes().observe(this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipesList) {
                mainActivityAdapter.setAdapter(recipesList);
                mRecipesList = recipesList;
                Log.d("Recipe size:", String.valueOf((mRecipesList.size())));

            }
        });
    }

    private int numOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int numColumns = width/widthDivider;
        if(numColumns < 2)return 1;
        return numColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
