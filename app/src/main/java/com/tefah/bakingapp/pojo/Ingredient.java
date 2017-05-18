package com.tefah.bakingapp.pojo;

/**
 * POJO to hold an ingredient's content
 */

public class Ingredient {
    private int quantity;
    private String ingredientName, measure;

    public Ingredient(int quantity, String measure, String ingredientName){
        this.quantity       = quantity;
        this.measure        = measure;
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
