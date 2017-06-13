package com.tefah.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;
    private Step step;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recipe =  Parcels.unwrap(intent.getParcelableExtra(String.valueOf(R.string.recipeKey)));
        setContentView(R.layout.activity_recipe_details);
    }
    public Recipe getRecipe(){
        return recipe;
    }

    public void setStep(Step step, int position) {
        this.step = step;
        this.position = position;
        updateUI();
    }

    public void updateUI(){
        Toast.makeText(this, "welcome again from activity" + step.getDescription(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StepDisplayActivity.class);

        intent.putExtra(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        intent.putExtra(String.valueOf(R.string.positionKey), position);
        startActivity(intent);
    }
    //// TODO: 5/29/2017 implement on save instance
    //// TODO: 6/12/2017 remove the toast
}
