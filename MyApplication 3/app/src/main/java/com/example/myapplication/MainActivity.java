package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etFamilyMembers, etHouseArea, etAdditionalHouseArea, etFossilFuelPercentage, etRenewablePercentage;
    private Spinner spinnerHouseType, spinnerPowerType;
    private Switch swElectricHeating;
    private String houseType, powerType;
    private int familyMembers;
    private double houseArea, additionalHouseArea, fossilFuelPercentage, renewablePercentage, greenElectricityPercentage;
    private boolean electricHeating;
    private double carDistance, bicycleDistance, publicTransportDistance, electricTransportDistance;
    private CarbonEmissionData carbonEmissionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etFamilyMembers = findViewById(R.id.etFamilyMembers);
        etHouseArea = findViewById(R.id.etHouseArea);
        etAdditionalHouseArea = findViewById(R.id.etAdditionalHouseArea);
        etFossilFuelPercentage = findViewById(R.id.etFossilFuelPercentage);
        etRenewablePercentage = findViewById(R.id.etRenewablePercentage);
        spinnerHouseType = findViewById(R.id.spinnerHouseType);
        spinnerPowerType = findViewById(R.id.spinnerPowerType);
        swElectricHeating = findViewById(R.id.swElectricHeating);
        Button nextButton = findViewById(R.id.btnNext);

        // Set up house type spinner
        ArrayAdapter<CharSequence> adapterHouseType = ArrayAdapter.createFromResource(this,
                R.array.house_type_array, android.R.layout.simple_spinner_item);
        adapterHouseType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHouseType.setAdapter(adapterHouseType);

        // Set up power type spinner
        ArrayAdapter<CharSequence> adapterPowerType = ArrayAdapter.createFromResource(this,
                R.array.power_types_array, android.R.layout.simple_spinner_item);
        adapterPowerType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPowerType.setAdapter(adapterPowerType);

        // Set visibility for percentage fields based on power type selection
        spinnerPowerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                powerType = parent.getItemAtPosition(position).toString();
                if (powerType.equals("Mixed power")) {
                    etRenewablePercentage.setVisibility(View.VISIBLE);
                    etFossilFuelPercentage.setVisibility(View.VISIBLE);
                } else if (powerType.equals("Pure green power")) {
                    etRenewablePercentage.setVisibility(View.GONE);
                    etFossilFuelPercentage.setVisibility(View.GONE);
                    greenElectricityPercentage = 100; // 100% green electricity
                } else {
                    etRenewablePercentage.setVisibility(View.GONE);
                    etFossilFuelPercentage.setVisibility(View.GONE);
                    greenElectricityPercentage = 0; // 0% green electricity
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                etRenewablePercentage.setVisibility(View.GONE);
                etFossilFuelPercentage.setVisibility(View.GONE);
                greenElectricityPercentage = 0; // 0% green electricity
            }
        });

        // Set visibility for heating consumption based on electric heating switch
        swElectricHeating.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etAdditionalHouseArea.setVisibility(View.GONE);
            } else {
                etAdditionalHouseArea.setVisibility(View.VISIBLE);
            }
        });

        // Set up next button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectInputData();
                carbonEmissionData = new CarbonEmissionData.Builder()
                        .setFamilyMembers(familyMembers)
                        .setHouseArea(houseArea)
                        .setAdditionalHouseArea(additionalHouseArea)
                        .setElectricityConsumption(calculateElectricityConsumption())
                        .setGreenElectricityPercentage(greenElectricityPercentage)
                        .setCarDistance(carDistance)
                        .setBicycleDistance(bicycleDistance)
                        .setPublicTransportDistance(publicTransportDistance)
                        .setElectricTransportDistance(electricTransportDistance)
                        .setFoodIntake(0) // Example value, replace with actual data
                        .setMeatPercentage(0) // Example value, replace with actual data
                        .setFoodCarbonEmission(0) // Example value, replace with actual data
                        .setCarCarbonEmission(0) // Example value, replace with actual data
                        .setPublicTransportCarbonEmission(0) // Example value, replace with actual data
                        .setElectricCarbonEmission(0) // Example value, replace with actual data
                        .build();
                Intent intent = new Intent(MainActivity.this,test.class);
                intent.putExtra("carbonEmissionData", carbonEmissionData);
                startActivity(intent);
            }
        });
    }

    private void collectInputData() {
        // Collect data from UI components
        familyMembers = Integer.parseInt(etFamilyMembers.getText().toString());
        houseArea = Double.parseDouble(etHouseArea.getText().toString());
        additionalHouseArea = Double.parseDouble(etAdditionalHouseArea.getText().toString());
        if (powerType.equals("Mixed power")) {
            fossilFuelPercentage = Double.parseDouble(etFossilFuelPercentage.getText().toString());
            renewablePercentage = Double.parseDouble(etRenewablePercentage.getText().toString());
            greenElectricityPercentage = renewablePercentage;
        } else {
            fossilFuelPercentage = 0;
            renewablePercentage = 0;
            if (powerType.equals("Pure green power")) {
                greenElectricityPercentage = 100; // 100% green electricity
            } else {
                greenElectricityPercentage = 0; // 0% green electricity
            }
        }
        electricHeating = swElectricHeating.isChecked();
        houseType = spinnerHouseType.getSelectedItem().toString();
    }

    private double calculateElectricityConsumption() {
        // Calculate electricity consumption based on house type and family size
        int X = familyMembers - 1;
        double electricityConsumption = 0;
        switch (houseType) {
            case "Apartment":
                electricityConsumption = 1400 + X * 500;
                break;
            case "Row house":
                electricityConsumption = 2600 + X * 700;
                break;
            case "Detached house":
                electricityConsumption = 4600 + X * 900;
                break;
        }
        return electricityConsumption;
    }

    private double calculateHeatingEnergyConsumption() {
        double totalHouseArea = houseArea + additionalHouseArea;
        return totalHouseArea * 241; // kWh/m2 per year
    }

    private double calculateHeatingCarbonEmissions() {
        if (electricHeating) {
            return 0; // No carbon emissions for electric heating
        } else {
            double heatingEnergyConsumption = calculateHeatingEnergyConsumption();
            return heatingEnergyConsumption * 0.267; // kg CO2eq/kWh for district heating
        }
    }
}
