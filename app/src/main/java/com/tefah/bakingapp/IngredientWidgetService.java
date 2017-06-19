package com.tefah.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tefah.bakingapp.pojo.Recipe;

import java.util.List;

/**
 * intent service to handle action of the ingredient widget
 */

public class IngredientWidgetService extends IntentService{

    public static final String ACTION_UPDATE_RECIPE_WIDGET = "update recipe";

    public IngredientWidgetService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        switch (intent.getAction()){
            case ACTION_UPDATE_RECIPE_WIDGET:
                handleUpdateRecipeWidget();
                break;
            default:
                Log.i("WIDGET_SERVICE", "invalid action");
        }

    }

    private void handleUpdateRecipeWidget() {
        List<Recipe> recipes = QueryUtils.getData();
        Recipe theOne = null;
        int id = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(this.getString(R.string.preference_recipe_id_key), 0);
        for (Recipe recipe : recipes)
            if (recipe.getId() == id)
                theOne = recipe;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_vie);
        //// TODO: 6/19/2017 notify data set changed here
        for (int appWidgetId : appWidgetIds) {
            IngredientWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetId, theOne);
        }
    }
    public static void startActionUpdateWidget(Context context){
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }
}
