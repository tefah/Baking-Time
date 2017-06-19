package com.tefah.bakingapp.UIs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tefah.bakingapp.R;
import com.tefah.bakingapp.UIs.RecipeDetailsActivity;
import com.tefah.bakingapp.adapters.IngredientAdapter;
import com.tefah.bakingapp.adapters.StepAdapter;
import com.tefah.bakingapp.pojo.Ingredient;
import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import java.util.List;

/**
 * master fragment to display the ingredients and steps
 */

public class RecipeDetailsMasterFragment extends Fragment implements StepAdapter.OnStepClick{

    private Recipe recipe;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;

    // Mandatory empty constructor
    public RecipeDetailsMasterFragment() {
    }

    // Inflates the two recycler views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
        RecipeDetailsActivity parent = (RecipeDetailsActivity) getActivity();
        recipe = parent.getRecipe();
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        final View rootView = inflater.inflate(R.layout.fragment_master_recipe_details, container, false);

        //get a refrence to the 2 recycler views
        RecyclerView ingredientsRecyclerView = (RecyclerView)
                rootView.findViewById(R.id.ingredientsRecyclerView);
        RecyclerView stepsRecyclerView = (RecyclerView)
                rootView.findViewById(R.id.recipeStepRecyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(parent.getBaseContext(), LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager stepsLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        ingredientAdapter   = new IngredientAdapter(ingredients);
        stepAdapter         = new StepAdapter(steps, this);

        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(stepAdapter);
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);

        // Return the root view
        return rootView;
    }

    @Override
    public void onClick(Step step, int position) {
        Toast.makeText(getContext(), step.getShortDescription(), Toast.LENGTH_SHORT).show();
        ((RecipeDetailsActivity)getActivity()).setStep(step, position);
    }
}
