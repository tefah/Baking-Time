package com.tefah.bakingapp.UIs;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tefah.bakingapp.R;
import com.tefah.bakingapp.pojo.Recipe;
import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;
    private int position;

    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recipe =  Parcels.unwrap(intent.getParcelableExtra(String.valueOf(R.string.recipeKey)));
        setContentView(R.layout.activity_recipe_details);
        setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        position = 0;
        if (findViewById(R.id.fragmentContainer)!= null){
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setVariables(argumentsBundle());
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragmentContainer, stepDetailFragment).commit();
        }
    }
    public Recipe getRecipe(){
        return recipe;
    }

    /**
     * set the position to be the new position of the chosen step and update the UI
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
        updateUI();
    }

    // update UI either on tablet replacing fragment or on mobile an launching new activity
    public void updateUI(){
        if (stepDetailFragment!=null){
            StepDetailFragment newFragment = new StepDetailFragment();
            newFragment.setVariables(argumentsBundle());
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit();
        }else {
            Intent intent = new Intent(this, StepDisplayActivity.class);
            intent.putExtra(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
            intent.putExtra(String.valueOf(R.string.positionKey), position);
            startActivity(intent);
        }
    }

    /**
     * make a bundle of the arguments (recipe , position)
     * @return bundle
     */
    private Bundle argumentsBundle(){
        Bundle args = new Bundle();
        args.putParcelable(String.valueOf(R.string.recipeKey), Parcels.wrap(recipe));
        args.putInt(String.valueOf(R.string.positionKey), position);
        return args;
    }
}
