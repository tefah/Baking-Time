package com.tefah.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import org.parceler.Parcels;

public class StepDisplayActivity extends AppCompatActivity {

    Step step;
    Recipe recipe;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_display);

        Intent intent  = getIntent();
        recipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra(String.valueOf(R.string.recipeKey)));
        position = intent.getIntExtra(String.valueOf(R.string.positionKey), 0);
        Bundle args = new Bundle();
        args.putParcelable(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        args.putInt(String.valueOf(R.string.positionKey), position);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer,stepDetailFragment).commit();

    }

}
