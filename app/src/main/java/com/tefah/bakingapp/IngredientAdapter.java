package com.tefah.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefah.bakingapp.pojo.Ingredient;

import java.util.List;

/**
 * custom adapter for ingredients
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    Context context;
    List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int itemId = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(itemId,parent,shouldAttachToParentImmediately);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        ((TextView) holder.itemView.findViewById(R.id.ingredientName)).setText(ingredient.getIngredientName());
        ((TextView) holder.itemView.findViewById(R.id.ingredientQuantity))
                .setText(String.valueOf(ingredient.getQuantity()));
        ((TextView) holder.itemView.findViewById(R.id.ingredientMeasure)).setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredients==null)
            return 0;
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{

        public IngredientViewHolder(View itemView) {
            super(itemView);
        }
    }
}
