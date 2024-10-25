package edu.gatech.seclass.jobcompare6300.storage;


import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;

public class Storage extends SQLiteOpenHelper {
    // Database related constants
    protected static final String DATABASE_SCHEMA = "CREATE TABLE IF NOT EXISTS jobs(json TEXT UNIQUE NOT NULL)";
    protected static final String DATABASE_NAME = "storage.db";
    protected static final String[] JSON_COLUMN_ARRAY = new String[]{"rowid", "json"};

    // SharedPreferences key denoting active Job, as stored in database
    protected static final String CURRENT_JOB_KEY = "CURRENT_JOB_ROWID";

    // SharedPreferences keys related to Comparison settings
    protected static final String SALARY_KEY = "salaryWeight";
    protected static final String BONUS_KEY = "bonusWeight";
    protected static final String TRAINING_KEY = "trainingWeight";
    protected static final String LEAVE_KEY = "leaveWeight";
    protected static final String TELEWORK_KEY = "teleworkWeight";

    // SharedPreferences keys for passing job selection ids between job comparison activities
    protected static final String JOB_ID1_KEY = "jobId1";
    protected static final String JOB_ID2_KEY = "jobId2";

    // Serialization attributes
    protected static final Gson gson = new Gson();
    protected SharedPreferences sharedPrefs;

    /**
     * Constructor to initalize Storage class
     * @param ctx Application default context, necessary to initialize database and SharedPreferences
     */
    public Storage(Context ctx) {
        super(ctx, DATABASE_NAME, null, 1);
        getWritableDatabase();
        sharedPrefs = getDefaultSharedPreferences(ctx);
    }

    /**
     * Retrieve jobId1 and jobId2 of jobs to compare
     * @return int array of the two job ids, loaded from SharedPreferences
     */
    public int[] retrieveComparisonJobIds() {
        int[] jobIds = new int[2];
        jobIds[0] = sharedPrefs.getInt(JOB_ID1_KEY, -1);
        jobIds[1] = sharedPrefs.getInt(JOB_ID2_KEY, -1);
        return jobIds;
    }


    /**
     * Store jobId1 and jobId2 of jobs to compare in SharedPreferences
     * @param jobIds New array of 2 int job ids to store
     * @return True on success, False otherwise
     */
    public boolean updateComparisonJobIds(int[] jobIds) {
        if(jobIds.length != 2) {
            return false;
        }
        try {
            final SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(JOB_ID1_KEY, jobIds[0]);
            editor.putInt(JOB_ID2_KEY, jobIds[1]);
            editor.apply();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve the stored preferences of weighted Job details
     * @return ComparisonSettings object, loaded from SharedPreferences
     */
    public ComparisonSettings retrieveSettings() {
        int salaryWeight = sharedPrefs.getInt(SALARY_KEY, 1);
        int bonusWeight = sharedPrefs.getInt(BONUS_KEY, 1);
        int trainingWeight = sharedPrefs.getInt(TRAINING_KEY, 1);
        int leaveWeight = sharedPrefs.getInt(LEAVE_KEY, 1);
        int teleworkWeight = sharedPrefs.getInt(TELEWORK_KEY, 1);

        try {
            return new ComparisonSettings(salaryWeight, bonusWeight, trainingWeight, leaveWeight, teleworkWeight);
        } catch (IllegalArgumentException e) {
            // Comparison settings have been corrupted, forced to reset to default
            ComparisonSettings settings = new ComparisonSettings();
            updateSettings(settings);
            return settings;
        }
    }

    /**
     * Store weighted preferences for Job details in SharedPreferences
     * @param settings New ComparisonSettings object to store
     * @return True on success, False otherwise
     */
    public boolean updateSettings(ComparisonSettings settings) {
        try {
            final SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(SALARY_KEY, settings.getSalaryWeight());
            editor.putInt(BONUS_KEY, settings.getBonusWeight());
            editor.putInt(TRAINING_KEY, settings.getTrainingWeight());
            editor.putInt(LEAVE_KEY, settings.getLeaveWeight());
            editor.putInt(TELEWORK_KEY, settings.getTeleworkWeight());
            editor.apply();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve stored Job offers from database
     * @return List of stored Job offers, or an empty List
     */
    public List<Job> retrieveJobOffers() {
        final SQLiteDatabase db = getReadableDatabase();
        List<Job> result = new ArrayList<>();

        try (Cursor cur = db.query("jobs", JSON_COLUMN_ARRAY, null, null, null, null, null)) {
            if (cur.moveToFirst()) {
                do {
                    String encoded = cur.getString(1);
                    Job entry = gson.fromJson(encoded, Job.class);
                    entry.rowId = cur.getInt(0);

                    result.add(entry);
                } while (cur.moveToNext());
            }
        }

        return result;
    }

    /***
     * Update SharedPreferences to reflect passed Job as the active
     * @param job New Job object to mark as user's current
     * @return True on successful update, False otherwise
     */
    public boolean setCurrentJob(Job job) {
        try {
            addOrUpdateJob(job);

            final SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putLong(CURRENT_JOB_KEY, job.rowId);
            editor.apply();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /***
     * Retrieve the active Job as marked in SharedPreferences
     * @return Job object marked as current, or null if not set
     */
    public Job retrieveCurrentJob() {
        // Retrieve current Job's rowid, stored in SharedPreferences
        final long rowid = sharedPrefs.getLong(CURRENT_JOB_KEY, -1);
        if (-1 != rowid) {
            return getJobById(rowid);
        }
        return null;
    }

    /**
     * Store a new or update and existing Job offer into database storage
     * @param job Job offer to store in database
     * @return True if Job was successfully updated or inserted, False otherwise
     */
    public boolean addOrUpdateJob(Job job) {
        final SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("json", gson.toJson(job, Job.class));
        if (job.rowId != -1) {
            values.put("rowid", job.rowId);
        }

        final long rowId = database.replace("jobs", null, values);
        if (rowId != -1) {
            job.rowId = rowId;
            return true;
        }
        return false;
    }

    public void removeJob(Job job) {
        final SQLiteDatabase db = getWritableDatabase();
        db.delete("jobs", "rowid=?", new String[]{String.valueOf(job.rowId)});

        final long curJobId = sharedPrefs.getLong(CURRENT_JOB_KEY, -1);
        if (curJobId == job.rowId) {
            final SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putLong(CURRENT_JOB_KEY, -1);
            editor.apply();
        }
    }

    /**
     * Helper function to retrieve a stored Job via the database's unique rowid primary key
     * @param rowId Integer of unique rowid key in database
     * @return Job object if found, null otherwise
     */
    public Job getJobById(long rowId) {
        final SQLiteDatabase database = getReadableDatabase();

        try (Cursor cur = database.query("jobs", JSON_COLUMN_ARRAY, "rowid=?", new String[]{String.valueOf(rowId)}, null, null, null);) {
            if (cur.moveToFirst()) {
                String encodedJob = cur.getString(1);
                Job job = gson.fromJson(encodedJob, Job.class);
                job.rowId = rowId;
                return job;
            }
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onCreate(db);
    }
}
