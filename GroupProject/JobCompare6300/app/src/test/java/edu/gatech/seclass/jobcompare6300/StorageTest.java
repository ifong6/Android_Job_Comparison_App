package edu.gatech.seclass.jobcompare6300;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

@RunWith(RobolectricTestRunner.class)
public class StorageTest {

    private Storage storage;
    private Job dummyJob;


    @Before
    public void setUp() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        storage = new Storage(activity);

        dummyJob = new Job("Foo", "Bar", "Baz", "Fizz", 100, 1.0f, 1.0f, 1, 1, 0);
    }

    @After
    public void tearDown() {
        storage.close();
    }

    @Test
    public void testDatabaseRoundTrip() {
        assertTrue(storage.retrieveJobOffers().isEmpty());

        assertTrue(storage.addOrUpdateJob(dummyJob));
        var results = storage.retrieveJobOffers();
        assertFalse(results.isEmpty());

        assertArrayEquals(new Job[]{dummyJob}, results.toArray());
    }

    @Test
    public void testDatabaseUpdate() {
        assertTrue(storage.addOrUpdateJob(dummyJob));
        final String newTitle = "Something New";

        dummyJob.setTitle(newTitle);
        storage.addOrUpdateJob(dummyJob);

        var results = storage.retrieveJobOffers();
        assertEquals(1, results.size());
        assertEquals(newTitle, results.get(0).getTitle());
    }

    @Test
    public void testRemoveJob() {
        assertTrue(storage.addOrUpdateJob(dummyJob));
        var results = storage.retrieveJobOffers();
        assertEquals(1, results.size());

        storage.removeJob(dummyJob);
        assertTrue(storage.retrieveJobOffers().isEmpty());
    }

    @Test
    public void testRemovingCurrentJobClearsEntry() {
        assertTrue(storage.addOrUpdateJob(dummyJob));
        assertTrue(storage.setCurrentJob(dummyJob));
        assertEquals(dummyJob, storage.retrieveCurrentJob());

        storage.removeJob(dummyJob);
        assertNull(storage.retrieveCurrentJob());
    }

    @Test
    public void testCurrentJob() {
        assertNull(storage.retrieveCurrentJob());

        storage.addOrUpdateJob(dummyJob);
        assertNull(storage.retrieveCurrentJob());

        assertTrue(storage.setCurrentJob(dummyJob));
        assertEquals(dummyJob, storage.retrieveCurrentJob());
    }

    @Test
    public void testDefaultSettings() {
        final ComparisonSettings settings = storage.retrieveSettings();
        assertEquals(1, settings.getSalaryWeight());
        assertEquals(1, settings.getBonusWeight());
        assertEquals(1, settings.getTrainingWeight());
        assertEquals(1, settings.getLeaveWeight());
        assertEquals(1, settings.getTeleworkWeight());
    }

    @Test
    public void testUpdateSettings() {
        final ComparisonSettings old = storage.retrieveSettings();

        ComparisonSettings newSettings = new ComparisonSettings(1, 2, 3, 4, 5);
        storage.updateSettings(newSettings);

        assertNotEquals(old, storage.retrieveSettings());
        assertEquals(newSettings, storage.retrieveSettings());
    }

    @Test
    public void testEnsureJobUniqueness() {
        for (int i = 0; i < 5; i++) {
            // Cannot re-add the same job
            // These are treated as updates, even though the Job _object_ is new each iteration
            Job entry = new Job("A", "B", "C", "D", 1, 1f, 1f, 1f, 1, 1);
            assertTrue(storage.addOrUpdateJob(entry));
        }
        assertEquals(1, storage.retrieveJobOffers().size());

        // First rowId set is 1, so start loop at 2 to attempt to begin with unique rowId
        for (int i = 2; i <= 5; i++) {
            // Even if we explicitly (cheat and) set the rowId to a unique value, cannot add
            Job entry = new Job("A", "B", "C", "D", 1, 1f, 1f, 1f, 1, 1);
            entry.rowId = i;
            storage.addOrUpdateJob(entry);
        }

        assertEquals(1, storage.retrieveJobOffers().size());
    }

    @Test
    public void testMultipleOffers() {
        assertTrue(storage.addOrUpdateJob(dummyJob));
        assertEquals(1, storage.retrieveJobOffers().size());

        Job another = new Job("A", "B", "C", "D", 1, 1f, 1f, 1f, 1, 1);
        assertTrue(storage.addOrUpdateJob(another));

        assertEquals(2, storage.retrieveJobOffers().size());
    }

    @Test
    public void testSettingCurrentJobAddsToDatabase() {
        assertTrue(storage.retrieveJobOffers().isEmpty());
        storage.setCurrentJob(dummyJob);
        assertFalse(storage.retrieveJobOffers().isEmpty());
    }
}
