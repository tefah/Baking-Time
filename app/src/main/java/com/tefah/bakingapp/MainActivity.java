package com.tefah.bakingapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.tefah.bakingapp.adapters.RecipeAdapter;
import com.tefah.bakingapp.pojo.Recipe;

import org.parceler.Parcels;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeAdapter.OnRecipeClick{

    public static final int LOADER_ID   = 1;
    public static final int TABLET_DPI  = 600;

    RecyclerView recipesRecyclerView;
    GridLayoutManager layoutManager;
    RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // http://alvinalexander.com/android/how-to-determine-android-screen-size-dimensions-orientation
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi;

        recipesRecyclerView = (RecyclerView) findViewById(R.id.recipesRecyclerView);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                densityDpi >= TABLET_DPI){
            layoutManager = new GridLayoutManager(this,3);
        } else
            layoutManager = new GridLayoutManager(this, 1);
        recipesRecyclerView.setLayoutManager(layoutManager);
        recipesRecyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(this, null, this);
        recipesRecyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return new RecipesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        adapter.setRecipes(recipes);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        loader.forceLoad();
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        startActivity(intent);
    }
}
