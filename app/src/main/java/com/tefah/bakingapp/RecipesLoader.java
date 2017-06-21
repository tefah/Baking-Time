package com.tefah.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import com.tefah.bakingapp.IdlingResource.SimpleIdlingResource;
import com.tefah.bakingapp.pojo.Recipe;

import java.util.List;

/**
 * loader to handle background tasks
 */

public class RecipesLoader extends AsyncTaskLoader<List<Recipe>>{

    List<Recipe> recipes;
    @Nullable
    final SimpleIdlingResource mIdlingResource;

    public RecipesLoader(Context context, @Nullable final SimpleIdlingResource idlingResource) {
        super(context);
        mIdlingResource = idlingResource;
    }

    @Override
    protected void onStartLoading() {
        if (recipes != null)
            deliverResult(recipes);
        else {
            if (mIdlingResource != null)
                mIdlingResource.setIdleState(false);
            forceLoad();
        }
    }

    @Override
    public List<Recipe> loadInBackground() {
        return QueryUtils.getData();
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        this.recipes = data;
        super.deliverResult(data);
        if (mIdlingResource != null)
            mIdlingResource.setIdleState(true);
    }
}
