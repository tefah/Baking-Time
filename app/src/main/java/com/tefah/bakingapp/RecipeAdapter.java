package com.tefah.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefah.bakingapp.pojo.Recipe;

import java.util.List;

/**
 * custom adapter to display recipes
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent    The ViewGroup that these ViewHolders are contained within.
     * @param viewType
     *
     * @return A new RecipeViewHolder that holds the View for each list item
     */
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int itemId = R.layout.item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(itemId,parent,shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    /**
     * this method called by the recycler view to update the view with data
     * passing the view holder should be updated
     *
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String recipeName = recipes.get(position).getName();
        ((TextView)holder.itemView.findViewById(R.id.recipeNameTextView)).setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (recipes == null)
            return 0;
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        public RecipeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
