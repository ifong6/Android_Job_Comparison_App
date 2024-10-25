package edu.gatech.seclass.jobcompare6300;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;

public class ComparisonSettingsUnitTest {

    ComparisonSettings dummySettings;

    @Before
    public void setUp() {
        // Create a dummy instance to test setting of illegal values
        dummySettings = new ComparisonSettings();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSalaryWeightMustBePositive() {
        dummySettings.setSalaryWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSalaryWeightCannotExceed9() {
        dummySettings.setSalaryWeight(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBonusWeightMustBePositive() {
        dummySettings.setBonusWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBonusWeightCannotExceed9() {
        dummySettings.setBonusWeight(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrainingDevFundWeightMustBePositive() {
        dummySettings.setTrainingDevFundWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrainingDevFundWeightCannotExceed9() {
        dummySettings.setTrainingDevFundWeight(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaveTimeWeightMustBePositive() {
        dummySettings.setLeaveTimeWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaveTimeWeightCannotExceed9() {
        dummySettings.setLeaveTimeWeight(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeleworkDaysWeightMustBePositive() {
        dummySettings.setTeleworkDaysWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTeleworkDaysWeightCannotExceed9() {
        dummySettings.setTeleworkDaysWeight(10);
    }

}