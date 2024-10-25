package edu.gatech.seclass.jobcompare6300;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

public class EnterCurrentJobActivity extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSave;
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
        if(inputLivingCostInt < 0)
            inputLivingCostInt = 0;
        float inputYearlySalaryFloat = getInputFloat(inputYearlySalary);
        if(inputYearlySalaryFloat < 0)
            inputYearlySalaryFloat = 0;
        float inputYearlyBonusFloat = getInputFloat(inputYearlyBonus);
        if(inputYearlyBonusFloat < 0)
            inputYearlyBonusFloat = 0;
        float inputTDFInt = getInputFloat(inputTDF);
        if(inputTDFInt < 0)
            inputTDFInt = 0;
        int inputLeaveTimeInt = getInputInt(inputLeaveTime);
        if(inputLeaveTimeInt < 0)
            inputLeaveTimeInt = 0;
        int inputTeleworkDaysInt = getInputInt(inputTeleworkDays);
        if(inputTeleworkDaysInt < 0)
            inputTeleworkDaysInt = 0;

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
        if(inputTitleString.isEmpty()) {
            inputTitle.setError("Title required.");
            isValid = false;
        }

        String inputCompanyString = inputCompany.getText().toString();
        if(inputCompanyString.isEmpty()) {
            inputCompany.setError("Company required.");
            isValid = false;
        }

        String inputCityString = inputCity.getText().toString();
        if(inputCityString.isEmpty()) {
            inputCity.setError("City required.");
            isValid = false;
        }

        String inputStateString = inputState.getText().toString();
        if(inputStateString.isEmpty()) {
            inputState.setError("State required.");
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
        setContentView(R.layout.activity_enter_current_job);

        inputTitle = (EditText) findViewById(R.id.Edit_Title_CurJob);
        inputCompany = (EditText) findViewById(R.id.Edit_Company_CurJob);
        inputCity = (EditText) findViewById(R.id.Edit_City_CurJob);
        inputState = (EditText) findViewById(R.id.Edit_State_CurJob);
        inputLivingCost = (EditText) findViewById(R.id.Edit_Cost_CurJob);
        inputYearlySalary = (EditText) findViewById(R.id.Edit_Salary_CurJob);
        inputYearlyBonus = (EditText) findViewById(R.id.Edit_Bonus_CurJob);
        inputTDF = (EditText) findViewById(R.id.Edit_Fund_CurJob);
        inputLeaveTime = (EditText) findViewById(R.id.Edit_LeaveTime_CurJob);
        inputTeleworkDays = (EditText) findViewById(R.id.Edit_TeleworkDays_CurJob);

        // initialize a handle for the shared storage
        storage = new Storage(this);
        Job currentJob = storage.retrieveCurrentJob();

        // update the ui with loaded job or default if no job yet stored
        if(currentJob != null)
            SetJobUI(currentJob);
        else
            SetJobDefaultUI();

        // set up button click listener callbacks
        buttonCancel = (Button) findViewById(R.id.cancelButton_curJob);
        buttonSave = (Button) findViewById(R.id.saveButton_curJob);
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

    }

    protected void handleClickCurrentJobSave() {
        // check if the ui fields have a valid job.
        if(IsJobValidUI()) {
            // delete the current job in storage
            Job currentJob = storage.retrieveCurrentJob();
            if(currentJob != null)
                storage.removeJob(currentJob);

            // save the current job to storage from the UI fields
            Job jobUI = getJobFromUI();
            storage.setCurrentJob(jobUI);
            storage.close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    protected void handleClickCurrentJobCancel() {
        // return to main menu
        storage.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
