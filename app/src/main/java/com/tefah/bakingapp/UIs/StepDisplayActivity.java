package com.tefah.bakingapp.UIs;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tefah.bakingapp.R;
import com.tefah.bakingapp.pojo.Recipe;
import org.parceler.Parcels;

public class StepDisplayActivity extends AppCompatActivity {

    Recipe recipe;
    private int position;
    private boolean init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_display);

        if (savedInstanceState!=null){
            recipe      = Parcels.unwrap(savedInstanceState.getParcelable(String.valueOf(R.string.recipeKey)));
            position    = savedInstanceState.getInt(String.valueOf(R.string.positionKey), 0);
            init        = savedInstanceState.getBoolean(String.valueOf(R.string.initialKey));
        }else {
            Intent intent = getIntent();
            recipe = Parcels.unwrap(getIntent().getParcelableExtra(String.valueOf(R.string.recipeKey)));
            position = intent.getIntExtra(String.valueOf(R.string.positionKey), 0);
            init = true;
        }
        Bundle args = new Bundle();
        args.putParcelable(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        args.putInt(String.valueOf(R.string.positionKey), position);

        FragmentManager fm = getSupportFragmentManager();
        if (init) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setVariables(args);
            fm.beginTransaction().add(R.id.fragmentContainer, stepDetailFragment).commit();
        }else {
            StepDetailFragment newFragment = new StepDetailFragment();
            newFragment.setVariables(args);
            fm.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit();
        }
        init = false;
    }

    public void setPosition(int position){
        this.position = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        outState.putInt(String.valueOf(R.string.positionKey), position);
        outState.putBoolean(String.valueOf(R.string.initialKey), init);
        super.onSaveInstanceState(outState);
    }
}
