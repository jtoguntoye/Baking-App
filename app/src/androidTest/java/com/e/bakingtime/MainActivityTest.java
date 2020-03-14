package com.e.bakingtime;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;;
import androidx.test.espresso.action.ViewActions.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void test_isActivityInView() {
        //    ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.main)).check(matches(isDisplayed()));

    }

    @Test
    public void test_recipeClickIntent(){
        //1. find the view
        //2. perform the action on the view
        //3. Check if the view did what you expected
        onView(withId(R.id.recipe_recycler))
                .perform(actionOnItemAtPosition(3,click()));

        onView(withId(R.id.item_list)).check(matches(isDisplayed()));


    }

    @Test
    public void test_backNavigationToMainActivity(){

        onView(withId(R.id.recipe_recycler))
                .perform(actionOnItemAtPosition(3,click()));

        onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));


        pressBack();
        onView(withId(R.id.recipe_recycler)).check(matches(isDisplayed()));
    }
}
