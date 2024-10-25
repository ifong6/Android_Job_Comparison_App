package edu.gatech.seclass.jobcompare6300;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

public class ComparisonTableActivity extends AppCompatActivity {
    private Button buttonMainMenu;
    private Button buttonAnotherComparison;
    private TextView textTitleA, textTitleB;
    private TextView textCompanyA, textCompanyB;
    private TextView textLocationA, textLocationB;
    private TextView textYearlySalaryA, textYearlySalaryB;
    private TextView textYearlyBonusA, textYearlyBonusB;
    private TextView textTDFA, textTDFB;
    private TextView textLeaveTimeA, textLeaveTimeB;
    private TextView textTeleworkDaysA, textTeleworkDaysB;

    private Storage storage;

    private void SetJobUI(Job job1, Job job2) {
        /*
        Sets the UI text fields, populated from job1 and job2.
         */
        if (job1 == null || job2 == null)
            return;

        // first clear the UI, as we will leave any zero values blank.
        SetJobDefaultUI();

        textTitleA.setText(job1.getTitle());
        textTitleB.setText(job2.getTitle());

        textCompanyA.setText(job1.getCompany());
        textCompanyB.setText(job2.getCompany());

        String locationA = job1.getCity() + ", " + job1.getState();
        String locationB = job2.getCity() + ", " + job2.getState();
        textLocationA.setText(locationA);
        textLocationB.setText(locationB);

        textYearlySalaryA.setText(Float.toString(job1.getSalary()));
        textYearlySalaryB.setText(Float.toString(job2.getSalary()));

        textYearlyBonusA.setText(Float.toString(job1.getBonus()));
        textYearlyBonusB.setText(Float.toString(job2.getBonus()));

        textTDFA.setText(Float.toString(job1.getTrainingDevFund()));
        textTDFB.setText(Float.toString(job2.getTrainingDevFund()));

        textLeaveTimeA.setText(Integer.toString(job1.getLeaveTime()));
        textLeaveTimeB.setText(Integer.toString(job2.getLeaveTime()));

        textTeleworkDaysA.setText(Integer.toString(job1.getTeleworkDays()));
        textTeleworkDaysB.setText(Integer.toString(job2.getTeleworkDays()));


    }

    private void SetJobDefaultUI() {
        /*
        Sets the UI text fields, clearing all fields.
         */
        textTitleA.setText("");
        textTitleB.setText("");
        textCompanyA.setText("");
        textCompanyB.setText("");
        textLocationA.setText("");
        textLocationB.setText("");
        textYearlySalaryA.setText("");
        textYearlySalaryB.setText("");
        textYearlyBonusA.setText("");
        textYearlyBonusB.setText("");
        textTDFA.setText("");
        textTDFB.setText("");
        textLeaveTimeA.setText("");
        textLeaveTimeB.setText("");
        textTeleworkDaysA.setText("");
        textTeleworkDaysB.setText("");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_table);

        // set up button click listener callbacks
        buttonMainMenu = (Button) findViewById(R.id.comparisonTableMainMenu_button);
        buttonAnotherComparison = (Button) findViewById(R.id.comparisonTableAnotherComparison_button);

        textTitleA = (TextView) findViewById(R.id.Title_A);
        textTitleB = (TextView) findViewById(R.id.Title_B);
        textCompanyA = (TextView) findViewById(R.id.Company_A);
        textCompanyB = (TextView) findViewById(R.id.Company_B);
        textLocationA = (TextView) findViewById(R.id.Location_A);
        textLocationB = (TextView) findViewById(R.id.Location_B);
        textYearlySalaryA = (TextView) findViewById(R.id.YearlySalary_A);
        textYearlySalaryB = (TextView) findViewById(R.id.YearlySalary_B);
        textYearlyBonusA = (TextView) findViewById(R.id.YearlyBonus_A);
        textYearlyBonusB = (TextView) findViewById(R.id.YearlyBonus_B);
        textTDFA = (TextView) findViewById(R.id.Fund_A);
        textTDFB = (TextView) findViewById(R.id.Fund_B);
        textLeaveTimeA = (TextView) findViewById(R.id.LeaveTimeA);
        textLeaveTimeB = (TextView) findViewById(R.id.LeaveTimeB);
        textTeleworkDaysA = (TextView) findViewById(R.id.TeleworkDays_A);
        textTeleworkDaysB = (TextView) findViewById(R.id.TeleworkDays_B);


        // initialize a handle for the shared storage
        storage = new Storage(this);

        // get the job compare row ids from storage
        List<Job> jobOffers = storage.retrieveJobOffers();
        if(jobOffers.size() >= 2)
        {
            int[] jobRowIds = storage.retrieveComparisonJobIds();

            Job job1 = storage.getJobById(jobRowIds[0]);
            Job job2 = storage.getJobById(jobRowIds[1]);

            if(job1 == null || job2 == null)
                SetJobDefaultUI();
            else
                SetJobUI(job1, job2);
        }


        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickMainMenu();
            }
        });
        buttonAnotherComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickAnotherComparison();
            }
        });
    }


    protected void handleClickMainMenu() {
        storage.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void handleClickAnotherComparison() {
        storage.close();
        Intent intent = new Intent(this, CompareJobOffersActivity.class);
        startActivity(intent);
    }



}
