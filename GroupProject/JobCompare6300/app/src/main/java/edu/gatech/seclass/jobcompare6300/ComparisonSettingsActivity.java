package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.models.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.storage.Storage;

public class ComparisonSettingsActivity extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSave;
    private SeekBar inputYearlySalarySeekbar;
    private TextView textYearlySalary;
    private SeekBar inputYearlyBonusSeekbar;
    private TextView textYearlyBonus;
    private SeekBar inputTDFSeekbar;
    private TextView textTDF;
    private SeekBar inputLeaveTimeSeekbar;
    private TextView textLeaveTime;
    private SeekBar inputTeleworkDaysSeekbar;
    private TextView textTeleworkDays;

    private Storage storage;


    private int getInputInt(EditText inputTextBox) {
        String string_val = inputTextBox.getText().toString();
        if(!string_val.isEmpty())
            return Integer.parseInt(string_val);
        else
            return 0;
    }

    private ComparisonSettings getSettingsFromUI() {
        /*
        Reads the UI text fields and populates a new ComparisonSettings object.
        Returns a new ComparisonSettings object with all UI fields.
         */
        int inputYearlySalaryInt = inputYearlySalarySeekbar.getProgress();
        int inputYearlyBonusInt = inputYearlyBonusSeekbar.getProgress();
        int inputTDFInt = inputTDFSeekbar.getProgress();
        int inputLeaveTimeInt = inputLeaveTimeSeekbar.getProgress();
        int inputTeleworkDaysInt = inputTeleworkDaysSeekbar.getProgress();

        return new ComparisonSettings(inputYearlySalaryInt, inputYearlyBonusInt, inputTDFInt,
                inputLeaveTimeInt, inputTeleworkDaysInt);
    }

    private void SetUI(ComparisonSettings newSettings) {
        /*
        Sets the UI text fields, populated from newSettings.
        */
        inputYearlySalarySeekbar.setProgress(newSettings.getSalaryWeight());
        textYearlySalary.setText(Integer.toString(newSettings.getSalaryWeight()));

        inputYearlyBonusSeekbar.setProgress(newSettings.getBonusWeight());
        textYearlyBonus.setText(Integer.toString(newSettings.getBonusWeight()));

        inputTDFSeekbar.setProgress(newSettings.getTrainingWeight());
        textTDF.setText(Integer.toString(newSettings.getTrainingWeight()));

        inputLeaveTimeSeekbar.setProgress(newSettings.getLeaveWeight());
        textLeaveTime.setText(Integer.toString(newSettings.getLeaveWeight()));

        inputTeleworkDaysSeekbar.setProgress(newSettings.getTeleworkWeight());
        textTeleworkDays.setText(Integer.toString(newSettings.getTeleworkWeight()));
    }

    private void SetDefaultUI() {
        /*
        Sets the UI text fields, all values == 1.
         */
        //inputYearlySalary.setText("1");
        inputYearlySalarySeekbar.setProgress(1);
        textYearlySalary.setText("1");
        textYearlyBonus.setText("1");
        textTDF.setText("1");
        textLeaveTime.setText("1");
        textTeleworkDays.setText("1");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_comparison_settings);

        inputYearlySalarySeekbar = (SeekBar) findViewById(R.id.inputYearlySalarySeekbar);
        textYearlySalary = (TextView) findViewById(R.id.textYearlySalaryValue);
        inputYearlyBonusSeekbar = (SeekBar) findViewById(R.id.inputYearlyBonusSeekbar);
        textYearlyBonus = (TextView) findViewById(R.id.textYearlyBonusValue);
        inputTDFSeekbar = (SeekBar) findViewById(R.id.inputTDFSeekbar);
        textTDF = (TextView) findViewById(R.id.textTDFValue);
        inputLeaveTimeSeekbar = (SeekBar) findViewById(R.id.inputLeaveTimeSeekbar);
        textLeaveTime = (TextView) findViewById(R.id.textLeaveTimeValue);
        inputTeleworkDaysSeekbar = (SeekBar) findViewById(R.id.inputTeleworkDaysSeekbar);
        textTeleworkDays = (TextView) findViewById(R.id.textLeaveTeleworkDaysValue);

        // initialize a handle for the shared storage
        storage = new Storage(this);
        ComparisonSettings settings = storage.retrieveSettings();

        // update the ui with loaded job or default if no job yet stored
        if(settings != null)
            SetUI(settings);
        else
            SetDefaultUI();

        // set up button click listener callbacks
        buttonCancel = (Button) findViewById(R.id.cancelButton_Settings);
        buttonSave = (Button) findViewById(R.id.saveButton_Settings);
        setButtonOverrides();

        // add seekbar overrides to update the numeric text view
        // as the user drags the seekbar / slider
        setSeekbarOverrides();
    }


    protected void handleClickSave() {
        // save the current job to storage from the UI fields
        ComparisonSettings settingsUI = getSettingsFromUI();
        storage.updateSettings(settingsUI);
        storage.close();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    protected void handleClickCancel() {
        // return to main menu
        storage.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setButtonOverrides() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCancel();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickSave();
            }
        });
    }

    private void setSeekbarOverrides() {

        inputYearlySalarySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textYearlySalary.setText(Integer.toString(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        inputYearlyBonusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textYearlyBonus.setText(Integer.toString(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        inputTDFSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textTDF.setText(Integer.toString(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        inputLeaveTimeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textLeaveTime.setText(Integer.toString(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        inputTeleworkDaysSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textTeleworkDays.setText(Integer.toString(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}
