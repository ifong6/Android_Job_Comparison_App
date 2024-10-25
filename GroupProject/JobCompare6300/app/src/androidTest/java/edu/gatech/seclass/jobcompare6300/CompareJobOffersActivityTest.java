package edu.gatech.seclass.jobcompare6300;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
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
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobComparator;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompareJobOffersActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void clearStorage(){
        ApplicationProvider.getApplicationContext().deleteDatabase("storage.db");
    }

    @Test
    public void compareJobOffersActivityTest() {
        List<Job> jobList = new ArrayList<Job>();

        //Add current Job
        Job CurrJob = new Job("MyJob", "MyCo", "MyCity", "MyState", 100, 1000f, 1f, 100f, 0, 0);

        onView(withId(R.id.curJobButton_menu)).perform(click());
        onView(withId(R.id.Edit_Title_CurJob)).perform(replaceText(CurrJob.getTitle()), closeSoftKeyboard());
        onView(withId(R.id.Edit_Company_CurJob)).perform(replaceText(CurrJob.getCompany()), closeSoftKeyboard());
        onView(withId(R.id.Edit_City_CurJob)).perform(replaceText(CurrJob.getCity()), closeSoftKeyboard());
        onView(withId(R.id.Edit_State_CurJob)).perform(replaceText(CurrJob.getState()), closeSoftKeyboard());
        onView(withId(R.id.Edit_Cost_CurJob)).perform(replaceText(String.valueOf(CurrJob.getCostOfLiving())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Salary_CurJob)).perform(replaceText(String.valueOf(CurrJob.getSalary())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Bonus_CurJob)).perform(replaceText(String.valueOf(CurrJob.getBonus())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Fund_CurJob)).perform(replaceText(String.valueOf(CurrJob.getTrainingDevFund())), closeSoftKeyboard());
        onView(withId(R.id.Edit_LeaveTime_CurJob)).perform(replaceText(String.valueOf(CurrJob.getLeaveTime())), closeSoftKeyboard());
        onView(withId(R.id.Edit_TeleworkDays_CurJob)).perform(replaceText(String.valueOf(CurrJob.getTeleworkDays())), closeSoftKeyboard());
        onView(withId(R.id.saveButton_curJob)).perform(click());

        // Make sure all settings are set to 1
        onView(withId(R.id.settingButton_menu)).perform(click());
        onView(withId(R.id.inputYearlySalarySeekbar)).perform(setValue(1));
        onView(withId(R.id.inputYearlyBonusSeekbar)).perform(setValue(1));
        onView(withId(R.id.inputTDFSeekbar)).perform(setValue(1));
        onView(withId(R.id.inputLeaveTimeSeekbar)).perform(setValue(1));
        onView(withId(R.id.inputTeleworkDaysSeekbar)).perform(setValue(1));
        onView(withId(R.id.saveButton_Settings)).perform(click());


        Job dummyJob1 = new Job("Job1", "Co1", "C1", "St1", 95, 11000f, 1f, 100f, 1, 0);
        Job dummyJob2 = new Job("Job2", "Co2", "C2", "St2", 95, 10000f, 10000f, 100f, 2, 0);
        Job dummyJob3 = new Job("Job3", "Co3", "C3", "St3", 100, 11000f, 1f, 100f, 3, 0);
        Job dummyJob4 = new Job("Job4", "Co4", "C4", "St4", 100, 10000f, 1000f, 100f, 4, 0);

        addJob(dummyJob1);
        addJob(dummyJob2);
        addJob(dummyJob3);
        addJob(dummyJob4);

        jobList.add(CurrJob);
        jobList.add(dummyJob1);
        jobList.add(dummyJob2);
        jobList.add(dummyJob3);
        jobList.add(dummyJob4);


        ComparisonSettings settings = new ComparisonSettings();
        jobList.sort(new JobComparator(settings));
        checkJobs(jobList, 0, 1);
        checkJobs(jobList, 0, 2);
        checkJobs(jobList, 0, 3);
        checkJobs(jobList, 1, 2);
        checkJobs(jobList, 1, 3);
        checkJobs(jobList, 2, 3);
    }

    private static void checkJobs(List<Job> jobList, int job1Pos, int job2Pos){
        Job jobA;
        Job jobB;
        int jobAPos;
        int jobBPos;

        if(job1Pos > job2Pos){
            jobA = jobList.get(job2Pos);
            jobB = jobList.get(job1Pos);
            jobAPos = job2Pos;
            jobBPos = job1Pos;
        }else{
            jobA = jobList.get(job1Pos);
            jobB = jobList.get(job2Pos);
            jobAPos = job1Pos;
            jobBPos = job2Pos;
        }
        //select two jobs
        onView(withId(R.id.compareButton_menu)).perform(click());

        onView(allOf(withId(R.id.jobListJobDescription), childAtPosition(childAtPosition(withId(R.id.jobListRecyclerView),jobAPos),0))).perform(click());
        onView(allOf(withId(R.id.jobListJobDescription), childAtPosition(childAtPosition(withId(R.id.jobListRecyclerView),jobBPos),0))).perform(click());

        onView(withId(R.id.jobListRecyclerView)).perform(actionOnItemAtPosition(jobAPos, click()));
        onView(withId(R.id.jobListRecyclerView)).perform(actionOnItemAtPosition(jobBPos, click()));

        //check compared job info matches jobs
        onView(withId(R.id.compareButton_Compare)).perform(click());
        onView(withId(R.id.Title_A)).check(matches(withText(jobA.getTitle())));
        onView(withId(R.id.Title_B)).check(matches(withText(jobB.getTitle())));

        onView(withId(R.id.Company_A)).check(matches(withText(jobA.getCompany())));
        onView(withId(R.id.Company_B)).check(matches(withText(jobB.getCompany())));

        onView(withId(R.id.Location_A)).check(matches(withText(jobA.getCity() + ", " + jobA.getState())));
        onView(withId(R.id.Location_B)).check(matches(withText(jobB.getCity() + ", " + jobB.getState())));

        onView(withId(R.id.YearlySalary_A)).check(matches(withText(String.valueOf(jobA.getSalary()))));
        onView(withId(R.id.YearlySalary_B)).check(matches(withText(String.valueOf(jobB.getSalary()))));

        onView(withId(R.id.Fund_A)).check(matches(withText(String.valueOf(jobA.getTrainingDevFund()))));
        onView(withId(R.id.Fund_B)).check(matches(withText(String.valueOf(jobB.getTrainingDevFund()))));

        onView(withId(R.id.LeaveTimeA)).check(matches(withText(String.valueOf(jobA.getLeaveTime()))));
        onView(withId(R.id.LeaveTimeB)).check(matches(withText(String.valueOf(jobB.getLeaveTime()))));

        onView(withId(R.id.TeleworkDays_A)).check(matches(withText(String.valueOf(jobA.getTeleworkDays()))));
        onView(withId(R.id.TeleworkDays_B)).check(matches(withText(String.valueOf(jobB.getTeleworkDays()))));

        onView(withId(R.id.comparisonTableMainMenu_button)).perform(click());

    }

    private static void addJob(Job dummyJob){
        onView(withId(R.id.jobOfferButton_menu)).perform(click());
        onView(withId(R.id.Edit_Title_Offer)).perform(replaceText(dummyJob.getTitle()), closeSoftKeyboard());
        onView(withId(R.id.Edit_Company_Offer)).perform(replaceText(dummyJob.getCompany()), closeSoftKeyboard());
        onView(withId(R.id.Edit_City_Offer)).perform(replaceText(dummyJob.getCity()), closeSoftKeyboard());
        onView(withId(R.id.Edit_State_Offer)).perform(replaceText(dummyJob.getState()), closeSoftKeyboard());
        onView(withId(R.id.Edit_Cost_Offer)).perform(replaceText(String.valueOf(dummyJob.getCostOfLiving())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Salary_Offer)).perform(replaceText(String.valueOf(dummyJob.getSalary())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Bonus_Offer)).perform(replaceText(String.valueOf(dummyJob.getBonus())), closeSoftKeyboard());
        onView(withId(R.id.Edit_Fund_Offer)).perform(replaceText(String.valueOf(dummyJob.getTrainingDevFund())), closeSoftKeyboard());
        onView(withId(R.id.Edit_LeaveTime_Offer)).perform(replaceText(String.valueOf(dummyJob.getLeaveTime())), closeSoftKeyboard());
        onView(withId(R.id.Edit_TeleworkDays_Offer)).perform(replaceText(String.valueOf(dummyJob.getTeleworkDays())), closeSoftKeyboard());
        onView(withId(R.id.saveButton_Offer)).perform(click());
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



}
