package com.tefah.bakingapp.UIs;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tefah.bakingapp.IdlingResource.SimpleIdlingResource;
import com.tefah.bakingapp.widget.IngredientWidgetService;
import com.tefah.bakingapp.R;
import com.tefah.bakingapp.RecipesLoader;
import com.tefah.bakingapp.adapters.RecipeAdapter;
import com.tefah.bakingapp.pojo.Recipe;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeAdapter.OnRecipeClick{

    public static final int LOADER_ID   = 1;
    public static final int TABLET_DPI  = 600;

    @Nullable
    private SimpleIdlingResource idlingResource;

    @BindView(R.id.recipesRecyclerView)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.error_message_tv)
    TextView errorMessage;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    GridLayoutManager layoutManager;
    RecipeAdapter adapter;
    LoaderManager loaderManager;


    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource(){
        if (idlingResource == null)
            idlingResource = new SimpleIdlingResource();
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // http://alvinalexander.com/android/how-to-determine-android-screen-size-dimensions-orientation
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi;


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                densityDpi >= TABLET_DPI){
            layoutManager = new GridLayoutManager(this,3);
        } else
            layoutManager = new GridLayoutManager(this, 1);
        recipesRecyclerView.setLayoutManager(layoutManager);
        recipesRecyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(this, null, this);
        recipesRecyclerView.setAdapter(adapter);
        loaderManager = getLoaderManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return new RecipesLoader(this,loadingIndicator, idlingResource);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        loadingIndicator.setVisibility(View.GONE);
        if (recipes!=null){
            showDataOnScreen();
            adapter.setRecipes(recipes);
        }else
            showErrorMessage();
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        loader.forceLoad();
    }

    @Override
    public void onClick(Recipe recipe) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(this.getString(R.string.preference_recipe_id_key), recipe.getId());
        editor.commit();
        IngredientWidgetService.startActionUpdateWidget(this);

        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        startActivity(intent);
    }
    public void showDataOnScreen(){
        recipesRecyclerView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
    }
    public void showErrorMessage(){
        recipesRecyclerView.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
