package com.tefah.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tefah.bakingapp.pojo.Recipe;

import org.parceler.Parcels;

public class RecipeDetailesActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detailes);

        Intent intent = getIntent();
        recipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
    }
}
