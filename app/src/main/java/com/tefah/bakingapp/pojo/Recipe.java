package com.tefah.bakingapp.pojo;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * POJO class to hold a recipe content
 */

@Parcel
public class Recipe {

    int id, servings;
    String name, imageUrl;
    List<Step> steps;
    List<Ingredient> ingredients;

    // empty constructor for parceler library
    public Recipe(){}

    public Recipe(int id, int servings, String name, String imageUrl,
                  List<Step> steps, List<Ingredient> ingredients){
        this.id             = id;
        this.servings       = servings;
        this.name           = name;
        this.imageUrl       = imageUrl;
        this.steps          = steps;
        this.ingredients    = ingredients;
    }

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
