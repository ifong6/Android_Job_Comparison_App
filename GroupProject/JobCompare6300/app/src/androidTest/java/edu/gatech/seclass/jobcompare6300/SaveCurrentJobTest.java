package edu.gatech.seclass.jobcompare6300;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import edu.gatech.seclass.jobcompare6300.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SaveCurrentJobTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void saveCurrentJobTest() {
        onView(withId(R.id.curJobButton_menu)).perform(click());
        onView(withId(R.id.Edit_Title_CurJob)).perform(replaceText("Test Engineer"), closeSoftKeyboard());
        onView(withId(R.id.Edit_Company_CurJob)).perform(replaceText("Ga Co"), closeSoftKeyboard());
        onView(withId(R.id.Edit_City_CurJob)).perform(replaceText("Atlanta"), closeSoftKeyboard());
        onView(withId(R.id.Edit_State_CurJob)).perform(replaceText("GA"), closeSoftKeyboard());
        onView(withId(R.id.Edit_Cost_CurJob)).perform(replaceText("102"), closeSoftKeyboard());
        onView(withId(R.id.Edit_Salary_CurJob)).perform(replaceText("90000"), closeSoftKeyboard());
        onView(withId(R.id.Edit_Bonus_CurJob)).perform(replaceText("5000"), closeSoftKeyboard());
        onView(withId(R.id.Edit_Fund_CurJob)).perform(replaceText("5000"), closeSoftKeyboard());
        onView(withId(R.id.Edit_LeaveTime_CurJob)).perform(replaceText("80"), closeSoftKeyboard());
        onView(withId(R.id.Edit_TeleworkDays_CurJob)).perform(replaceText("2"), closeSoftKeyboard());

        onView(withId(R.id.saveButton_curJob)).perform(click());
        onView(withId(R.id.curJobButton_menu)).perform(click());
    
        onView(withId(R.id.Edit_Title_CurJob)).check(matches(withText("Test Engineer")));
        onView(withId(R.id.Edit_Company_CurJob)).check(matches(withText("Ga Co")));
        onView(withId(R.id.Edit_City_CurJob)).check(matches(withText("Atlanta")));
        onView(withId(R.id.Edit_State_CurJob)).check(matches(withText("GA")));
        onView(withId(R.id.Edit_Cost_CurJob)).check(matches(withText("102")));
        onView(withId(R.id.Edit_Salary_CurJob)).check(matches(withText("90000.0")));
        onView(withId(R.id.Edit_Bonus_CurJob)).check(matches(withText("5000.0")));
        onView(withId(R.id.Edit_Fund_CurJob)).check(matches(withText("5000.0")));
        onView(withId(R.id.Edit_LeaveTime_CurJob)).check(matches(withText("80")));
        onView(withId(R.id.Edit_TeleworkDays_CurJob)).check(matches(withText("2")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
}
