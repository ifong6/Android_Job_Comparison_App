package edu.gatech.seclass.jobcompare6300;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CancelCurrentJobTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void cancelCurrentJobTest() {
        ViewInteraction materialButton = onView(withId(R.id.curJobButton_menu));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(withId(R.id.Edit_Title_CurJob));
        String TitleText = getText(withId(R.id.Edit_Title_CurJob));
        appCompatEditText.perform(replaceText("Test Engineer"), closeSoftKeyboard());


        ViewInteraction appCompatEditText2 = onView(withId(R.id.Edit_Company_CurJob));
        String CompanyText = getText(withId(R.id.Edit_Company_CurJob));
        appCompatEditText2.perform(replaceText("Ga Co"), closeSoftKeyboard());


        ViewInteraction appCompatEditText3 = onView(withId(R.id.Edit_City_CurJob));
        String CityText = getText(withId(R.id.Edit_City_CurJob));
        appCompatEditText3.perform(replaceText("Atlanta"), closeSoftKeyboard());


        ViewInteraction appCompatEditText4 = onView(withId(R.id.Edit_State_CurJob));
        String StateText = getText(withId(R.id.Edit_State_CurJob));
        appCompatEditText4.perform(replaceText("GA"), closeSoftKeyboard());


        ViewInteraction appCompatEditText5 = onView(withId(R.id.Edit_Cost_CurJob));
        String CostText = getText(withId(R.id.Edit_Cost_CurJob));
        appCompatEditText5.perform(replaceText("102"), closeSoftKeyboard());


        ViewInteraction appCompatEditText6 = onView(withId(R.id.Edit_Salary_CurJob));
        String SalaryText = getText(withId(R.id.Edit_Salary_CurJob));
        appCompatEditText6.perform(replaceText("90000"), closeSoftKeyboard());


        ViewInteraction appCompatEditText7 = onView(withId(R.id.Edit_Bonus_CurJob));
        String BonusText = getText(withId(R.id.Edit_Bonus_CurJob));
        appCompatEditText7.perform(replaceText("5000"), closeSoftKeyboard());


        ViewInteraction appCompatEditText9 = onView(withId(R.id.Edit_Fund_CurJob));
        String FundText = getText(withId(R.id.Edit_Fund_CurJob));
        appCompatEditText9.perform(replaceText("5000"), closeSoftKeyboard());


        ViewInteraction appCompatEditText10 = onView(withId(R.id.Edit_LeaveTime_CurJob));
        String LeaveText = getText(withId(R.id.Edit_LeaveTime_CurJob));
        appCompatEditText10.perform(replaceText("80"), closeSoftKeyboard());


        ViewInteraction appCompatEditText11 = onView(withId(R.id.Edit_TeleworkDays_CurJob));
        String TeleworkText = getText(withId(R.id.Edit_TeleworkDays_CurJob));
        appCompatEditText11.perform(replaceText("2"), closeSoftKeyboard());



        //Cancel Job so information is not saved.
        ViewInteraction materialButton2 = onView(withId(R.id.cancelButton_curJob));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(withId(R.id.curJobButton_menu));
        materialButton3.perform(click());

        // Check no information changed
        ViewInteraction editText = onView(withId(R.id.Edit_Title_CurJob));
        editText.check(matches(withText(TitleText)));

        ViewInteraction editText2 = onView(withId(R.id.Edit_Company_CurJob));
        editText2.check(matches(withText(CompanyText)));

        ViewInteraction editText3 = onView(withId(R.id.Edit_City_CurJob));
        editText3.check(matches(withText(CityText)));

        ViewInteraction editText4 = onView(withId(R.id.Edit_State_CurJob));
        editText4.check(matches(withText(StateText)));

        ViewInteraction editText5 = onView(withId(R.id.Edit_Cost_CurJob));
        editText5.check(matches(withText(CostText)));

        ViewInteraction editText6 = onView(withId(R.id.Edit_Salary_CurJob));
        editText6.check(matches(withText(SalaryText)));

        ViewInteraction editText7 = onView(withId(R.id.Edit_Bonus_CurJob));
        editText7.check(matches(withText(BonusText)));

        ViewInteraction editText8 = onView(withId(R.id.Edit_Fund_CurJob));
        editText8.check(matches(withText(FundText)));

        ViewInteraction editText9 = onView(withId(R.id.Edit_LeaveTime_CurJob));
        editText9.check(matches(withText(LeaveText)));

        ViewInteraction editText10 = onView(withId(R.id.Edit_TeleworkDays_CurJob));
        editText10.check(matches(withText(TeleworkText)));
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /* BEGIN CODE FROM https://stackoverflow.com/questions/23381459/how-to-get-text-from-textview-using-espresso */
    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
        /* END CODE FROM https://stackoverflow.com/questions/23381459/how-to-get-text-from-textview-using-espresso */
    }
}
