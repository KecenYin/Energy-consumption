package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class test extends AppCompatActivity {

    private TextView carDistanceDisplay, bicycleDistanceDisplay, publicTransportDistanceDisplay, electricTransportDistanceDisplay, meatPercentageDisplay;
    private EditText editTextFoodIntake;
    private double carDistance, bicycleDistance, publicTransportDistance, electricTransportDistance, foodIntake, meatPercentage;
    private CarbonEmissionData carbonEmissionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        carDistanceDisplay = findViewById(R.id.textCarDistance);
        bicycleDistanceDisplay = findViewById(R.id.textBicycleDistance);
        publicTransportDistanceDisplay = findViewById(R.id.textPublicTransportDistance);
        electricTransportDistanceDisplay = findViewById(R.id.textElectricTransportDistance);
        editTextFoodIntake = findViewById(R.id.editTextFoodIntake);
        meatPercentageDisplay = findViewById(R.id.textMeatPercentage);
        Button submitButton = findViewById(R.id.btnSubmit);

        carbonEmissionData = CarbonEmissionDataHolder.getInstance().getCarbonEmissionData();

        setupSeekBar(R.id.seekBarCar, carDistanceDisplay, "Car: %d km");
        setupSeekBar(R.id.seekBarBicycle, bicycleDistanceDisplay, "Bicycle: %d km");
        setupSeekBar(R.id.seekBarPublicTransport, publicTransportDistanceDisplay, "Public Transport: %d km");
        setupSeekBar(R.id.seekBarElectricTransport, electricTransportDistanceDisplay, "Electric/Intercity: %d km");

        SeekBar seekBarMeatPercentage = findViewById(R.id.seekBarMeatPercentage);
        seekBarMeatPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                meatPercentage = progress;
                meatPercentageDisplay.setText(String.format("Meat: %d%%", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectInputData();
                double foodCarbonEmissions = calculateFoodCarbonEmissions();
                double carCarbonEmissions = carDistance * 0.097; // Car emissions in kg CO2 per km
                double publicTransportCarbonEmissions = publicTransportDistance * 0.068; // Public transport emissions in kg CO2 per km
                double electricCarbonEmissions = electricTransportDistance * 0.001; // Electric transport emissions in kg CO2 per km

                carbonEmissionData = new CarbonEmissionData.Builder()
                        .setFamilyMembers(carbonEmissionData.getFamilyMembers())
                        .setHouseArea(carbonEmissionData.getHouseArea())
                        .setElectricityConsumption(carbonEmissionData.getElectricityConsumption())
                        .setGreenElectricityPercentage(carbonEmissionData.getGreenElectricityPercentage())
                        .setCarDistance(carDistance)
                        .setBicycleDistance(bicycleDistance)
                        .setPublicTransportDistance(publicTransportDistance)
                        .setElectricTransportDistance(electricTransportDistance)
                        .setFoodIntake(foodIntake)
                        .setMeatPercentage(meatPercentage)
                        .setFoodCarbonEmission(foodCarbonEmissions)
                        .setCarCarbonEmission(carCarbonEmissions)
                        .setPublicTransportCarbonEmission(publicTransportCarbonEmissions)
                        .setElectricCarbonEmission(electricCarbonEmissions)
                        .build();

                CarbonEmissionDataHolder.getInstance().setCarbonEmissionData(carbonEmissionData);

                Intent intent = new Intent(test.this, ResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSeekBar(int seekBarId, TextView display, String format) {
        SeekBar seekBar = findViewById(seekBarId);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int distance = (int) (900 * (progress / 100.0));  // Calculate the actual distance
                if (seekBarId == R.id.seekBarCar) {
                    carDistance = distance;
                } else if (seekBarId == R.id.seekBarBicycle) {
                    bicycleDistance = distance;
                } else if (seekBarId == R.id.seekBarPublicTransport) {
                    publicTransportDistance = distance;
                } else if (seekBarId == R.id.seekBarElectricTransport) {
                    electricTransportDistance = distance;
                }
                display.setText(String.format(format, distance));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void collectInputData() {
        foodIntake = 30 * Double.parseDouble(editTextFoodIntake.getText().toString());
    }

    private double calculateFoodCarbonEmissions() {
        double meatEmission = 15.0;
        double averageFoodEmission = 2.5;

        double meatIntake = foodIntake * (meatPercentage / 100.0);
        double nonMeatIntake = foodIntake - meatIntake;

        return (meatIntake * meatEmission + nonMeatIntake * averageFoodEmission) / 1000; // 转换为kg CO2
    }
}
