package com.tefah.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * master fragment to display the ingredients and steps
 */

public class RecipeDetailesMasterFragment extends Fragment {
    // Mandatory empty constructor
    public RecipeDetailesMasterFragment() {
    }

    // Inflates the two recycler views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_recipe_detailes, container, false);

        //get a refrence to the 2 recycler views
        RecyclerView ingredientsRecyclerView = (RecyclerView)
                rootView.findViewById(R.id.ingredientsRecyclerView);
        RecyclerView stepsRecyclerView = (RecyclerView)
                rootView.findViewById(R.id.recipeStepRecyclerView);

        // todo Create the adapters

        //todo Set the adapters on the recycler views

        //todo Handle the click of steps recycler view


        // Return the root view
        return rootView;
    }
}
