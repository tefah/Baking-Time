package com.tefah.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.tefah.bakingapp.pojo.Recipe;

import java.util.List;

/**
 * loader to handle background tasks
 */

public class RecipesLoader extends AsyncTaskLoader<List<Recipe>>{

    List<Recipe> recipes;

    public RecipesLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (recipes != null)
            deliverResult(recipes);
        else
            forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        return QueryUtils.getData();
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        this.recipes = data;
        super.deliverResult(data);
    }
}
