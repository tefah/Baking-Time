package com.tefah.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

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
    ProgressBar loadingIndicator;

    public RecipesLoader(Context context, ProgressBar loadingIndicator,
                         @Nullable final SimpleIdlingResource idlingResource) {
        super(context);
        mIdlingResource = idlingResource;
        this.loadingIndicator = loadingIndicator;
    }

    @Override
    protected void onStartLoading() {
        loadingIndicator.setVisibility(View.VISIBLE);
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
