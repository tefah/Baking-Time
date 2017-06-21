package com.tefah.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.tefah.bakingapp.UIs.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * class to test clicking on a recipe get me to the right activity
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void clickingRecyclerViewItem_opensRecipeDetailsActivity(){
       // https://guides.codepath.com/android/UI-Testing-with-Espresso#interacting-with-a-recyclerview
        onView(withId(R.id.recipesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }
    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null)
            Espresso.unregisterIdlingResources(idlingResource);
    }
}
