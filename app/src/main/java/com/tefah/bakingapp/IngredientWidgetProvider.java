package com.tefah.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.tefah.bakingapp.UIs.MainActivity;
import com.tefah.bakingapp.pojo.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);
//        intent.setAction(MainActivity.ACTION_CHOOSE_RECIPE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.fallTextView, pendingIntent);

//        views.setViewVisibility(R.id.emptyView, View.INVISIBLE);
//        if (recipe == null) {
//            Intent intent = new Intent(context, MainActivity.class);
//            intent.setAction(MainActivity.ACTION_CHOOSE_RECIPE);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            views.setViewVisibility(R.id.emptyView, View.VISIBLE);
//            views.setViewVisibility(R.id.widgetListView, View.INVISIBLE);
//            views.setOnClickPendingIntent(R.id.fallTextView, pendingIntent);
//        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientWidgetService.startActionUpdateWidget(context);
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

