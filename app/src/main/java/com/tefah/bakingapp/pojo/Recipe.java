package com.tefah.bakingapp.pojo;

import java.util.List;

/**
 * POJO class to hold a recipe content
 */

public class Recipe {

    private int id, servings;
    private String name, imageUrl;
    private List<Step> steps;
    private List<Ingredient> ingredients;

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
