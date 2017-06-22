package com.tefah.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tefah.bakingapp.R;

/**
 * intent service to handle action of the ingredient widget
 */

public class IngredientWidgetService extends IntentService{

    public static final String ACTION_UPDATE_RECIPE_WIDGET = "update recipe";

    public IngredientWidgetService() {
        super("IngredientWidgetService");
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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
        IngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, appWidgetIds);
    }
    public static void startActionUpdateWidget(Context context){
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }
}
