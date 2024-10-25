package edu.gatech.seclass.jobcompare6300;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobComparator;

public class JobComparatorTest {

    ComparisonSettings settings;
    JobComparator comparator;

    Job alpha;
    Job bravo;
    Job charlie;

    @Before
    public void setUp() {
        // Initialize to default weights (all 1)
        settings = new ComparisonSettings();

        // Create comparator with settings reference
        comparator = new JobComparator(settings);

        // Initialize jobs with simple differences in salary (only)
        alpha = new Job("Accountant", "Alpha Co.", "Anchorage", "AK", 100, 333, 1, 1, 1, 1);
        bravo = new Job("Brick Layer", "Bob's Bricks", "Baltimore", "B-State", 100, 22, 1, 1, 1, 1);
        charlie = new Job("Consultant", "Consult R Us", "Calabasas", "CA", 100, 1, 1, 1, 1, 1);
    }

    @Test
    public void defaultSettingsEqualWeight() {
        alpha.setYearlySalary(1);
        bravo.setYearlySalary(1);
        charlie.setYearlySalary(1);

        assertEquals(0, comparator.compare(alpha, bravo));
        assertEquals(0, comparator.compare(bravo, charlie));
        assertEquals(0, comparator.compare(alpha, charlie));
    }

    @Test
    public void ensureSortedOrdering() {
        final var expected = new Job[]{alpha, bravo, charlie};

        // All 6 (3!) permutations
        assertArrayEquals(expected, Stream.of(alpha, bravo, charlie).sorted(comparator).toArray());
        assertArrayEquals(expected, Stream.of(alpha, charlie, bravo).sorted(comparator).toArray());
        assertArrayEquals(expected, Stream.of(bravo, alpha, charlie).sorted(comparator).toArray());
        assertArrayEquals(expected, Stream.of(bravo, charlie, alpha).sorted(comparator).toArray());
        assertArrayEquals(expected, Stream.of(charlie, alpha, bravo).sorted(comparator).toArray());
        assertArrayEquals(expected, Stream.of(charlie, bravo, alpha).sorted(comparator).toArray());
    }

    @Test
    public void orderingWithZeroWeight() {
        // Mark charlie with a big bonus, sort, adjust settings to weigh bonus as irrelevant, resort
        charlie.setYearlyBonus(99999999);

        ArrayList<Job> jobList = new ArrayList<>(Arrays.asList(alpha, bravo, charlie));
        jobList.sort(comparator);
        assertArrayEquals(new Job[]{charlie, alpha, bravo}, jobList.toArray());

        settings.setBonusWeight(0);
        jobList.sort(comparator);
        assertArrayEquals(new Job[]{alpha, bravo, charlie}, jobList.toArray());
    }

    @Test
    public void lowCostOfLiving() {
        // Mark bravo with extremely low cost of living, which should jump it to top choice
        bravo.setCostOfLiving(1);

        ArrayList<Job> jobList = new ArrayList<>(Arrays.asList(alpha, bravo, charlie));
        jobList.sort(comparator);
        assertArrayEquals(new Job[]{bravo, alpha, charlie}, jobList.toArray());
    }
}