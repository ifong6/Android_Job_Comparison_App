package edu.gatech.seclass.jobcompare6300;


import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

@RunWith(RobolectricTestRunner.class)
public class enterOfferIntentTest {

    private RobolectricViewAssertions rva = new RobolectricViewAssertions();

    @Test
    public void canSaveOffer() {
        try (ActivityController<EnterJobOffersActivity> controller = Robolectric.buildActivity(EnterJobOffersActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            EnterJobOffersActivity activity = controller.get();

            Job dummyJob = new Job("Foo", "Bar", "Baz", "Fizz", 100, 1.0f, 1.0f, 1, 1, 0);
            enterJob(dummyJob, activity);
            activity.findViewById(R.id.saveButton_Offer).performClick();
            Intent expectedIntent = new Intent(activity, MainActivity.class);
            Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        }
    }

    @Test
    public void canCancelOffer() {
        try (ActivityController<EnterJobOffersActivity> controller = Robolectric.buildActivity(EnterJobOffersActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            EnterJobOffersActivity activity = controller.get();

            Job dummyJob = new Job("Foo", "Bar", "Baz", "Fizz", 100, 1.0f, 1.0f, 1, 1, 0);
            enterJob(dummyJob, activity);
            activity.findViewById(R.id.cancelButton_Offer).performClick();
            Intent expectedIntent = new Intent(activity, MainActivity.class);
            Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        }
    }

    @Test
    public void canEnterNewOffer() {
        try (ActivityController<EnterJobOffersActivity> controller = Robolectric.buildActivity(EnterJobOffersActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            EnterJobOffersActivity activity = controller.get();

            Job dummyJob = new Job("Foo", "Bar", "Baz", "Fizz", 100, 1.0f, 1.0f, 1, 1, 0);
            enterJob(dummyJob, activity);
            activity.findViewById(R.id.addButton_Offer).performClick();
            //Check if fields are clear
            rva.assertTextViewText(R.id.Edit_Title_Offer,"");
            rva.assertTextViewText(R.id.Edit_Company_Offer,"");
            rva.assertTextViewText(R.id.Edit_City_Offer,"");
            rva.assertTextViewText(R.id.Edit_State_Offer,"");
            rva.assertTextViewText(R.id.Edit_Cost_Offer,"");
            rva.assertTextViewText(R.id.Edit_Salary_Offer,"");
            rva.assertTextViewText(R.id.Edit_Bonus_Offer,"");
            rva.assertTextViewText(R.id.Edit_Fund_Offer,"");
            rva.assertTextViewText(R.id.Edit_LeaveTime_Offer,"");
            rva.assertTextViewText(R.id.Edit_TeleworkDays_Offer,"");

        }
    }

    @Test
    public void canCompareOffer() {
        try (ActivityController<EnterJobOffersActivity> controller = Robolectric.buildActivity(EnterJobOffersActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            EnterJobOffersActivity activity = controller.get();

            Job dummyJob = new Job("Foo", "Bar", "Baz", "Fizz", 100, 1.0f, 1.0f, 1, 1, 0);
            enterJob(dummyJob, activity);
            activity.findViewById(R.id.compareButton_Offer).performClick();
            Intent expectedIntent = new Intent(activity, CompareJobOffersActivity.class);
            Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());
        }
    }

    public void enterJob(Job testCase, EnterJobOffersActivity testActivity){
        rva.setActivity(testActivity);
        rva.replaceText(R.id.Edit_Title_Offer,testCase.getTitle());
        rva.replaceText(R.id.Edit_Company_Offer,testCase.getCompany());
        rva.replaceText(R.id.Edit_City_Offer,testCase.getCity());
        rva.replaceText(R.id.Edit_State_Offer,testCase.getState());
        rva.replaceText(R.id.Edit_Cost_Offer,String.valueOf(testCase.getCostOfLiving()));
        rva.replaceText(R.id.Edit_Salary_Offer,String.valueOf(testCase.getSalary()));
        rva.replaceText(R.id.Edit_Bonus_Offer,String.valueOf(testCase.getBonus()));
        rva.replaceText(R.id.Edit_Fund_Offer,String.valueOf(testCase.getTrainingDevFund()));
        rva.replaceText(R.id.Edit_LeaveTime_Offer,String.valueOf(testCase.getLeaveTime()));
        rva.replaceText(R.id.Edit_TeleworkDays_Offer,String.valueOf(testCase.getTeleworkDays()));

    }


}

