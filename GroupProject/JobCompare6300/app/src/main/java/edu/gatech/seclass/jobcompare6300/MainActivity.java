package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.seclass.jobcompare6300.storage.Storage;

public class MainActivity extends AppCompatActivity {

    // define four buttons in main menu
    private Button curJobButton;
    private Button jobOffersButton;
    private Button settingButton;
    private Button compareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curJobButton = (Button) findViewById(R.id.curJobButton_menu);
        jobOffersButton = (Button) findViewById(R.id.jobOfferButton_menu);
        settingButton = (Button) findViewById(R.id.settingButton_menu);
        compareButton = (Button) findViewById(R.id.compareButton_menu);

        Storage storage = new Storage(this);
        compareButton.setEnabled(storage.retrieveJobOffers().size() > 1);

        // routing
        // jump to view: activity_enter_current_job
        curJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        EnterCurrentJobActivity.class);
                startActivity(intent);
            }
        });

        // jump to view: activity_enter_job_offers
        jobOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        EnterJobOffersActivity.class);
                startActivity(intent);
            }
        });

        // jump to view: activity_adjust_comparison_settings
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ComparisonSettingsActivity.class);
                startActivity(intent);
            }
        });

        // jump to view: activity_compare_job_offers
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        CompareJobOffersActivity.class);
                startActivity(intent);
            }
        });
    }
}