package com.tefah.bakingapp;

import android.text.TextUtils;
import android.util.Log;

import com.tefah.bakingapp.pojo.Ingredient;
import com.tefah.bakingapp.pojo.Recipe;
import com.tefah.bakingapp.pojo.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * utility class for networking
 */

public class QueryUtils {
    public static final String RECIPE_URL = "http://go.udacity.com/android-baking-app-json";
    /**
     * make an HTTP request for the given url
     * @return string response tha data of recipes
     */
    private static String makeHttpRequest(){
        String jsonResponse = "";
        //https://github.com/codepath/android_guides/wiki/Using-OkHttp
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(RECIPE_URL)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unsuccessful request");
            }
            jsonResponse = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private static List<Recipe> fetchData(String response){
        //in case there is no response return null
        if (TextUtils.isEmpty(response))
            return null;
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(response);
            for(int i =0; i<root.length(); i++){
                JSONObject jsonRecipe = root.getJSONObject(i);
                int id                          = jsonRecipe.getInt("id");
                String name                     = jsonRecipe.getString("name");
                int servings                    = jsonRecipe.getInt("servings");
                String imageUrl                 = jsonRecipe.getString("image");
                JSONArray ingredientsArray      = jsonRecipe.getJSONArray("ingredients");
                JSONArray stepsArray            = jsonRecipe.getJSONArray("steps");
                List<Ingredient> ingredients = parseIngredient(ingredientsArray);
                List<Step> steps = parseSteps(stepsArray);

                Recipe recipe = new Recipe(id, servings, name, imageUrl, steps, ingredients);
                recipes.add(recipe);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    /**
     * parsing a jsonArray of steps into list of step object
     * @param stepsArray
     * @return list of steps
     * @throws JSONException
     */
    private static List<Step> parseSteps(JSONArray stepsArray) throws JSONException {
        List<Step> steps = new ArrayList<>();
        for (int j = 0; j<stepsArray.length(); j++){
            JSONObject jsonStep =  stepsArray.getJSONObject(j);

            int stepId              = jsonStep.getInt("id");
            String shortDescription = jsonStep.getString("shortDescription");
            String description      = jsonStep.getString("description");
            String videoUrl         = jsonStep.getString("videoURL");
            if (TextUtils.isEmpty(videoUrl))
                videoUrl = jsonStep.getString("thumbnailURL");

            Step step = new Step(stepId, shortDescription, description, videoUrl);
            steps.add(step);
        }
        return steps;
    }

    /**
     * parsing a jsonArray of ingredients into a list of ingredient object
     * @param ingredientsArray
     * @return
     * @throws JSONException
     */
    private static List<Ingredient> parseIngredient(JSONArray ingredientsArray) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();

        for (int j = 0; j<ingredientsArray.length(); j++){
            JSONObject jsonIngredient =  ingredientsArray.getJSONObject(j);

            int quantity            = jsonIngredient.getInt("quantity");
            String measure          = jsonIngredient.getString("measure");
            String ingredientName   = jsonIngredient.getString("ingredient");

            Ingredient ingredient = new Ingredient(quantity, measure, ingredientName);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public static List<Recipe> getData(){
        String response = makeHttpRequest();
        List<Recipe> recipes = fetchData(response);
        return recipes;
    }
}
