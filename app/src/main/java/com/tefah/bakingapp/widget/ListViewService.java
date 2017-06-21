package com.tefah.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tefah.bakingapp.QueryUtils;
import com.tefah.bakingapp.R;
import com.tefah.bakingapp.pojo.Ingredient;
import com.tefah.bakingapp.pojo.Recipe;
import java.util.List;

/**
 * Remote View Service to handle list view factory
 */

public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewFactory(this.getApplicationContext());
    }
}

 class ListViewFactory implements RemoteViewsService.RemoteViewsFactory{

    Context context;
    List<Ingredient> ingredients;

     public ListViewFactory(Context appContext){
         context = appContext;
     }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sp.getInt(context.getString(R.string.preference_recipe_id_key), 1);
        List<Recipe> recipes = QueryUtils.getData();
        ingredients = recipes.get(id-1).getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    // bind views in the list view like onBindViewHolder in a typical adapter
    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredients.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item);
        views.setTextViewText(R.id.ingredientName, ingredient.getIngredientName());
        views.setTextViewText(R.id.ingredientQuantity, String.valueOf(ingredient.getQuantity()));
        views.setTextViewText(R.id.ingredientMeasure, String.valueOf(ingredient.getMeasure()));

        return views;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}