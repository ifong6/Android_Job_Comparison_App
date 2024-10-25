package edu.gatech.seclass.jobcompare6300;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
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

import android.widget.SeekBar;
import android.widget.TextView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ComparisonSettingsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    //Test save settings function works
    @Test
    public void comparisonSettingsSaveTest() {
        onView(withId(R.id.settingButton_menu)).perform(click());

        onView(withId(R.id.inputYearlySalarySeekbar)).perform(setValue(3));
        onView(withId(R.id.inputYearlyBonusSeekbar)).perform(setValue(4));
        onView(withId(R.id.inputTDFSeekbar)).perform(setValue(6));
        onView(withId(R.id.inputLeaveTimeSeekbar)).perform(setValue(7));
        onView(withId(R.id.inputTeleworkDaysSeekbar)).perform(setValue(8));

        onView(withId(R.id.saveButton_Settings)).perform(click());
        onView(withId(R.id.settingButton_menu)).perform(click());

        onView(withId(R.id.inputYearlySalarySeekbar)).check(matches(withValue(3)));
        onView(withId(R.id.inputYearlyBonusSeekbar)).check(matches(withValue(4)));
        onView(withId(R.id.inputTDFSeekbar)).check(matches(withValue(6)));
        onView(withId(R.id.inputLeaveTimeSeekbar)).check(matches(withValue(7)));
        onView(withId(R.id.inputTeleworkDaysSeekbar)).check(matches(withValue(8)));

    }

    @Test
    public void comparisonSettingsCancelTest() {
        onView(withId(R.id.settingButton_menu)).perform(click());

        int SalarySetting = getValue(withId(R.id.inputYearlySalarySeekbar));
        int BonusSetting = getValue(withId(R.id.inputYearlyBonusSeekbar));
        int TDFSetting = getValue(withId(R.id.inputTDFSeekbar));
        int LeaveSetting = getValue(withId(R.id.inputLeaveTimeSeekbar));
        int TeleworkSetting = getValue(withId(R.id.inputTeleworkDaysSeekbar));


        onView(withId(R.id.inputYearlySalarySeekbar)).perform(setValue(3));
        onView(withId(R.id.inputYearlyBonusSeekbar)).perform(setValue(4));
        onView(withId(R.id.inputTDFSeekbar)).perform(setValue(6));
        onView(withId(R.id.inputLeaveTimeSeekbar)).perform(setValue(7));
        onView(withId(R.id.inputTeleworkDaysSeekbar)).perform(setValue(8));

        onView(withId(R.id.cancelButton_Settings)).perform(click());
        onView(withId(R.id.settingButton_menu)).perform(click());

        onView(withId(R.id.inputYearlySalarySeekbar)).check(matches(withValue(SalarySetting)));
        onView(withId(R.id.inputYearlyBonusSeekbar)).check(matches(withValue(BonusSetting)));
        onView(withId(R.id.inputTDFSeekbar)).check(matches(withValue(TDFSetting)));
        onView(withId(R.id.inputLeaveTimeSeekbar)).check(matches(withValue(LeaveSetting)));
        onView(withId(R.id.inputTeleworkDaysSeekbar)).check(matches(withValue(TeleworkSetting)));

    }


    /* BEGIN CODE FROM https://stackoverflow.com/questions/65390086/androidx-how-to-test-slider-in-ui-tests-espresso */
    public static Matcher<View> withValue(int expectedValue) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                if (view instanceof SeekBar) {
                    SeekBar slider = (SeekBar) view;
                    return slider.getProgress() == expectedValue;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("expected: " + expectedValue);
            }
        };
    }

    public static ViewAction setValue(int value) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return IsInstanceOf.instanceOf(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "Set Slider value to " + value;
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar slider = (SeekBar) view;
                slider.setProgress(value);
            }
        };
    }
    /* END CODE FROM https://stackoverflow.com/questions/65390086/androidx-how-to-test-slider-in-ui-tests-espresso */

    /* BEGIN CODE FROM https://stackoverflow.com/questions/23381459/how-to-get-text-from-textview-using-espresso */
    int getValue(final Matcher<View> matcher) {
        final int[] value = new int[1];
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "getting int from a SeekBar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar bar = (SeekBar) view; //Save, because of check in getConstraints()
                value[0] = bar.getProgress();
            }
        });
        return value[0];
        /* END CODE FROM https://stackoverflow.com/questions/23381459/how-to-get-text-from-textview-using-espresso */
    }



}
