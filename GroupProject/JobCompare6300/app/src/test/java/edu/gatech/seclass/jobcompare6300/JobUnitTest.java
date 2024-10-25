package edu.gatech.seclass.jobcompare6300;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.seclass.jobcompare6300.models.Job;

public class JobUnitTest {

    Job dummyJob;

    @Before
    public void setUp() {
        // Create a dummy instance to test setting of illegal values
        dummyJob = new Job("Jobby", "McCompany", "City", "State", 100, 1000f, 1f, 100f, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTitleCannotBeBlank() {
        dummyJob.setTitle("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompanyCannotBeBlank() {
        dummyJob.setCompany("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCityCannotBeBlank() {
        dummyJob.setLocationCity("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStateCannotBeBlank() {
        dummyJob.setLocationState("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCostOfLivingMustBePositive() {
        dummyJob.setCostOfLiving(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCostOfLivingCannotBeZero() {
        dummyJob.setCostOfLiving(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSalaryMustBePositive() {
        dummyJob.setYearlySalary(-0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBonusMustBePositive() {
        dummyJob.setYearlyBonus(-0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrainingMustBePositive() {
        dummyJob.setTrainingDevFund(-0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrianingCappedAtMaxmimum() {
        dummyJob.setTrainingDevFund(18000.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaveMustBePositive() {
        dummyJob.setLeaveTime(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaveCannotExceed100() {
        dummyJob.setLeaveTime(101);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeleworkMustBePositive() {
        dummyJob.setTeleworkDays(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeleworkCannotExceed7() {
        dummyJob.setTeleworkDays(8);
    }

}