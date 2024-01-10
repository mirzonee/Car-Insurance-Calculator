package com.example.carinsurancecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSumInsured, editTextEngineSize;
    private RadioButton radioButtonFirstParty, radioButtonThirdParty;
    private Spinner spinnerNCD;
    private Switch switchWindscreenCover;
    private Button buttonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSumInsured = findViewById(R.id.editTextSumInsured);
        editTextEngineSize = findViewById(R.id.editTextEngineSize);
        radioButtonFirstParty = findViewById(R.id.radioButtonFirstParty);
        radioButtonThirdParty = findViewById(R.id.radioButtonThirdParty);
        spinnerNCD = findViewById(R.id.spinnerNCD);
        switchWindscreenCover = findViewById(R.id.swWindscreenCover);
        buttonCalculate = findViewById(R.id.buttonCalculate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatePremium();
            }
        });
    }

    private void calculatePremium() {
        double sumInsured = Double.parseDouble(editTextSumInsured.getText().toString());
        int engineSize = Integer.parseInt(editTextEngineSize.getText().toString());
        String coverageType = radioButtonFirstParty.isChecked() ? "First Party" : "Third Party";
        boolean isWestMalaysia = ((RadioButton) findViewById(R.id.radioButtonWestMalaysia)).isChecked();

        // Retrieve the selected NCD value from the Spinner
        String selectedNCD = spinnerNCD.getSelectedItem().toString();
        double ncd = parseNCD(selectedNCD);

        // Check if windscreen cover is selected
        boolean hasWindscreenCover = switchWindscreenCover.isChecked();

        // Create an instance of the InsuranceCalculator class
        InsuranceCalculator calculator = new InsuranceCalculator();

        // Calculate the premium using the InsuranceCalculator class
        double premium = calculator.calculateBasicPremium(sumInsured, engineSize, coverageType, isWestMalaysia, ncd);

        // Apply NCD discount directly to the calculated basic premium
        premium = premium * (1 - ncd);

        // Add windscreen cover cost if selected
        if (hasWindscreenCover) {
            premium += 120.0;
        }

        // Create an Intent to start InsuranceDetailsActivity
        Intent intent = new Intent(this, ReportActivity.class);

        // Pass necessary data as extras
        intent.putExtra("IS_WEST_MALAYSIA", isWestMalaysia);
        intent.putExtra("SUM_INSURED", sumInsured);
        intent.putExtra("ENGINE_SIZE", engineSize);
        intent.putExtra("COVERAGE_TYPE", coverageType);
        intent.putExtra("NCD", selectedNCD);
        intent.putExtra("HAS_WINDSCREEN_COVER", hasWindscreenCover);
        intent.putExtra("PAYMENT_AMOUNT",premium);

        // Start the ReportActivity
        startActivity(intent);

    }

        private double parseNCD (String ncdString){
            return Double.parseDouble(ncdString.replace("%", "")) / 100.0;
        }
}
