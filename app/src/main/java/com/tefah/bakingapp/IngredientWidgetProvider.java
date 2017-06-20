package com.tefah.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import com.tefah.bakingapp.UIs.MainActivity;
import com.tefah.bakingapp.pojo.Ingredient;
import com.tefah.bakingapp.pojo.Recipe;

import org.parceler.Parcels;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sp.getInt(context.getString(R.string.preference_recipe_id_key), 0);
        RemoteViews views;
        if (id > 0)
        // Construct the RemoteViews object
            views = bind(context);
        else
            views = chooseRecipe(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // bind the list view to the adapter
    private static RemoteViews bind(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        Intent intent = new Intent(context, ListViewService.class);
        views.setRemoteAdapter(R.id.widgetListView, intent);
        views.setEmptyView(R.id.widgetListView,R.id.emptyView);
        return views;
    }

    // if there is no chosen recipe at the very first time
    private static RemoteViews chooseRecipe(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.fallTextView, pendingIntent);
        views.setViewVisibility(R.id.emptyView, View.VISIBLE);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientWidgetService.startActionUpdateWidget(context);
    }

    // update all instances of the widget
    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                                int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

