package edu.gatech.seclass.jobcompare6300;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;
import java.util.Comparator;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobComparator;
import edu.gatech.seclass.jobcompare6300.storage.Storage;
import edu.gatech.seclass.jobcompare6300.models.DataSelectModel;


public class CompareJobOffersActivity extends AppCompatActivity {
    private Button buttonMainMenu;
    private Button buttonCompare;

    private JobListRecyclerViewAdapter recyclerViewAdapter;

    // list of data model rows as stored in the recyclerViewAdapter
    // each holds a short description of Job
    // indexing is the same as (sorted) jobOffers list
    // all lists are populated at onCreate()
    private ArrayList<DataSelectModel> dataModelList;

    // list of Jobs as loaded from persisting storage
    private List<Job> jobOffers;
    ComparisonSettings settings;
    private Storage storage;

    private Job currentJob;

    private int getInputInt(EditText inputTextBox) {
        String string_val = inputTextBox.getText().toString();
        if(!string_val.isEmpty())
            return Integer.parseInt(string_val);
        else
            return -1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_job_offers);

        // initialize a handle for the shared storage
        storage = new Storage(this);

        // set up button click listener callbacks
        buttonMainMenu = (Button) findViewById(R.id.BackButton_Compare);
        buttonCompare = (Button) findViewById(R.id.compareButton_Compare);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jobListRecyclerView);

        jobOffers = storage.retrieveJobOffers();
        settings = storage.retrieveSettings();
        currentJob = storage.retrieveCurrentJob();

        // sort the jobs based on rank
        jobOffers.sort(new JobComparator(settings));

        // create a list of data models - each is a short description of
        // the job.  todo - make this prettier using a grid/table layout
        dataModelList = new ArrayList<>();

        // populate the description models for the RecyclerView
        ListIterator<Job> jobListIterator = jobOffers.listIterator();
        while(jobListIterator.hasNext()) {
            Job nextJob = jobListIterator.next();
            String jobDescription = nextJob.toString();
            //For making current job "clearly indicated"
            if (nextJob.equals(currentJob)){
                jobDescription = jobDescription + " \"CURRENT JOB\"";
            }

            dataModelList.add(new DataSelectModel(jobDescription));
        }

        // create the RecyclerView adapter, populate it with the data models
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new JobListRecyclerViewAdapter(this, dataModelList);
        recyclerView.setAdapter(recyclerViewAdapter);

        // set button callbacks
        setButtonOverrides();
    }

    private void setButtonOverrides() {
        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickMainMenu();
            }
        });
        buttonCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCompare();
            }
        });
    }

    protected void handleClickMainMenu() {
        storage.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void handleClickCompare() {

        // get the two active jobs from the selection RecyclerView list
        // error if there are not two jobs selected.
        List<Integer> jobIds = new ArrayList<>();

        for (int i=0; i<dataModelList.size(); i++) {
            if(dataModelList.get(i).getIsActive()){
                jobIds.add(i);
            }
        }

        if(jobIds.size() != 2) {
            Toast.makeText(this, "Please select two jobs.", Toast.LENGTH_SHORT).show();
        }
        else {
            // job ids are valid. save their corresponding job rowIds to storage for the
            // next comparison table activity.
            int[] jobIdArray = {(int)jobOffers.get(jobIds.get(0)).rowId,
                                (int)jobOffers.get(jobIds.get(1)).rowId};
            if(storage.updateComparisonJobIds(jobIdArray)) {
                storage.close();
                Intent intent = new Intent(this, ComparisonTableActivity.class);
                startActivity(intent);
            }
            else {
                // haven't been able to reproduce this.  Placed here for solidarity!
                Toast.makeText(this, "Unspecified Error.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
