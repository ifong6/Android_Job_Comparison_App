package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

public class EnterJobOffersActivity extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSave;
    private Button buttonSaveAdd;
    private Button buttonSaveCompare;
    private EditText inputTitle;
    private EditText inputCompany;
    private EditText inputCity;
    private EditText inputState;
    private EditText inputLivingCost;
    private EditText inputYearlySalary;
    private EditText inputYearlyBonus;
    private EditText inputTDF;
    private EditText inputLeaveTime;
    private EditText inputTeleworkDays;
    private Storage storage;

    private int getInputInt(EditText inputTextBox) {
        String string_val = inputTextBox.getText().toString();
        if(!string_val.isEmpty())
            return Integer.parseInt(string_val);
        else
            return -1;
    }

    private float getInputFloat(EditText inputTextBox) {
        String string_val = inputTextBox.getText().toString();
        if(!string_val.isEmpty())
            return Float.parseFloat(string_val);
        else
            return -1.f;
    }

    private Job getJobFromUI() {
        /*
        Reads the UI text fields and populates a new Job object.
        Returns a new Job object with all UI fields.
         */
        String inputTitleString = inputTitle.getText().toString();
        String inputCompanyString = inputCompany.getText().toString();
        String inputCityString = inputCity.getText().toString();
        String inputStateString = inputState.getText().toString();
        int inputLivingCostInt = getInputInt(inputLivingCost);
        float inputYearlySalaryFloat = getInputFloat(inputYearlySalary);
        float inputYearlyBonusFloat = getInputFloat(inputYearlyBonus);
        float inputTDFInt = getInputFloat(inputTDF);
        int inputLeaveTimeInt = getInputInt(inputLeaveTime);
        int inputTeleworkDaysInt = getInputInt(inputTeleworkDays);

        return new Job(inputTitleString, inputCompanyString, inputCityString,
                inputStateString, inputLivingCostInt, inputYearlySalaryFloat,
                inputYearlyBonusFloat, inputTDFInt, inputLeaveTimeInt, inputTeleworkDaysInt);
    }

    private boolean IsJobValidUI()
    {
        /*
        Check if the UI entry is a valid job.  returns true if valid.
         */
        boolean isValid = true;

        String inputTitleString = inputTitle.getText().toString();
        if(inputTitleString.isEmpty())
        {
            inputTitle.setError("Title required");
            isValid = false;
        }

        String inputCompanyString = inputCompany.getText().toString();
        if(inputCompanyString.isEmpty())
        {
            inputCompany.setError("Company required");
            isValid = false;
        }

        String inputCityString = inputCity.getText().toString();
        if(inputCityString.isEmpty())
        {
            inputCity.setError("City required");
            isValid = false;
        }

        String inputStateString = inputState.getText().toString();
        if(inputStateString.isEmpty())
        {
            inputState.setError("State required");
            isValid = false;
        }

        int inputLivingCostInt = getInputInt(inputLivingCost);
        if(inputLivingCostInt <= 0)  {
            inputLivingCost.setError("Living Cost invalid.");
            isValid = false;
        }

        float inputYearlySalaryFloat = getInputFloat(inputYearlySalary);
        if(inputYearlySalaryFloat < 0) {
            inputYearlySalary.setError("Salary invalid.");
            isValid = false;
        }

        float inputYearlyBonusFloat = getInputFloat(inputYearlyBonus);
        if(inputYearlyBonusFloat < 0) {
            inputYearlyBonus.setError("Bonus invalid.");
            isValid = false;
        }

        float inputTDFFloat = getInputFloat(inputTDF);
        if (0. > inputTDFFloat || inputTDFFloat > 18000.) {
            inputTDF.setError("Training Fund required.");
            isValid = false;
        }

        int inputLeaveTimeInt = getInputInt(inputLeaveTime);
        if (0 > inputLeaveTimeInt || inputLeaveTimeInt > 100) {
            inputLeaveTime.setError("Leave Time invalid.");
            isValid = false;
        }

        int inputTeleworkDaysInt = getInputInt(inputTeleworkDays);
        if (0 > inputTeleworkDaysInt || inputTeleworkDaysInt > 7) {
            inputTeleworkDays.setError("Telework days invalid.");
            isValid = false;
        }

        return isValid;
    }

    private void SetJobUI(Job currentJob) {
        /*
        Sets the UI text fields, populated from currentJob.
         */

        // first clear the UI, as we will leave any zero values blank.
        SetJobDefaultUI();

        inputTitle.setText(currentJob.getTitle());
        inputCompany.setText(currentJob.getCompany());
        inputCity.setText(currentJob.getCity());
        inputState.setText(currentJob.getState());
        inputLivingCost.setText(Integer.toString(currentJob.getCostOfLiving()));
        inputYearlySalary.setText(Float.toString(currentJob.getSalary()));
        inputYearlyBonus.setText(Float.toString(currentJob.getBonus()));
        inputTDF.setText(Float.toString(currentJob.getTrainingDevFund()));
        inputLeaveTime.setText(Integer.toString(currentJob.getLeaveTime()));
        inputTeleworkDays.setText(Integer.toString(currentJob.getTeleworkDays()));
    }
    private void SetJobDefaultUI() {
        /*
        Sets the UI text fields, clearing all fields.
         */
        inputTitle.setText("");
        inputCompany.setText("");
        inputCity.setText("");
        inputState.setText("");
        inputLivingCost.setText("");
        inputYearlySalary.setText("");
        inputYearlyBonus.setText("");
        inputTDF.setText("");
        inputLeaveTime.setText("");
        inputTeleworkDays.setText("");
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offers);


        inputTitle = (EditText) findViewById(R.id.Edit_Title_Offer);
        inputCompany = (EditText) findViewById(R.id.Edit_Company_Offer);
        inputCity = (EditText) findViewById(R.id.Edit_City_Offer);
        inputState = (EditText) findViewById(R.id.Edit_State_Offer);
        inputLivingCost = (EditText) findViewById(R.id.Edit_Cost_Offer);
        inputYearlySalary = (EditText) findViewById(R.id.Edit_Salary_Offer);
        inputYearlyBonus = (EditText) findViewById(R.id.Edit_Bonus_Offer);
        inputTDF = (EditText) findViewById(R.id.Edit_Fund_Offer);
        inputLeaveTime = (EditText) findViewById(R.id.Edit_LeaveTime_Offer);
        inputTeleworkDays = (EditText) findViewById(R.id.Edit_TeleworkDays_Offer);

        // initialize a handle for the shared storage
        storage = new Storage(this);
        Job currentJob = storage.retrieveCurrentJob();



        // clear all fields.
        SetJobDefaultUI();

        // set up button click listener callbacks
        buttonCancel = (Button) findViewById(R.id.cancelButton_Offer);
        buttonSave = (Button) findViewById(R.id.saveButton_Offer);
        buttonSaveAdd = (Button) findViewById(R.id.addButton_Offer);
        buttonSaveCompare = (Button) findViewById(R.id.compareButton_Offer);

        buttonSaveCompare.setEnabled(!storage.retrieveJobOffers().isEmpty());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCurrentJobCancel();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCurrentJobSave();
            }
        });
        buttonSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCurrentJobSaveAdd();
            }
        });
        buttonSaveCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCurrentJobSaveCompare();
            }
        });
    }

    protected void handleClickCurrentJobSave() {
        // check if the ui fields have a valid job.
        if(IsJobValidUI()) {
            // save the current job offer to storage from the UI fields.
            Job jobUI = getJobFromUI();
            storage.addOrUpdateJob(jobUI);
            storage.close();

            // return to main menu Activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    protected void handleClickCurrentJobSaveAdd() {
        // check if the ui fields have a valid job.
        if(IsJobValidUI()) {
            // save the current job offer to storage from the UI fields.
            Job jobUI = getJobFromUI();

            storage.addOrUpdateJob(jobUI);
            buttonSaveCompare.setEnabled(true);
            // remain in the current Activity.
            // clear the job offer fields so the user may add another job offer.
            SetJobDefaultUI();
        }
    }

    protected void handleClickCurrentJobSaveCompare() {
        // check if the ui fields have a valid job.
        if(IsJobValidUI()) {
            // save the current job offer to storage from the UI fields.
            Job jobUI = getJobFromUI();
            storage.addOrUpdateJob(jobUI);
            storage.close();

            // open the job compare UI in new Activity.
            Intent intent = new Intent(this, CompareJobOffersActivity.class);
            startActivity(intent);
        }
    }

    protected void handleClickCurrentJobCancel() {
        // return to main menu. do not save the job offer from the UI fields.
        storage.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}
