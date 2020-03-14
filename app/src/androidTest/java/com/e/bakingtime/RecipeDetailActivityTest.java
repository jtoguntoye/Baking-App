package com.e.bakingtime;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> DetailActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void is_RecipeDetailVisible(){
       onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));
    }
    @Test
    public void is_DetailFragmentPresent(){
        onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.item_list)).check(matches(isDisplayed()));

    }


}