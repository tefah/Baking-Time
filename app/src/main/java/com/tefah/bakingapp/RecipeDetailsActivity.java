package com.tefah.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;
    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        setContentView(R.layout.activity_recipe_details);
    }
    public Recipe getRecipe(){
        return recipe;
    }

    public void setStep(Step step) {
        this.step = step;
        updateUI();
    }

    public void updateUI(){
        Toast.makeText(this, "welcome again from activity" + step.getDescription(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StepDisplayActivity.class);
        intent.putExtra("step", step);
        startActivity(intent);
    }
    //// TODO: 5/29/2017 implement on save instance
    //// TODO: 6/12/2017 remove the toast
}
