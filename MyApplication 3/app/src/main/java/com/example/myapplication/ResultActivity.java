package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvElectricityResult, tvMonthlyElectricityEmission, tvFoodCarbonEmission, tvHeatingCarbonEmission,
            tvTotalResult, tvCarEmissionPercentage, tvPublicTransportEmissionPercentage, tvElectricEmissionPercentage,
            tvFoodEmissionPercentage, tvElectricityEmissionPercentage, tvHeatingEmissionPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize UI components
        tvElectricityResult = findViewById(R.id.tvElectricityResult);
        tvMonthlyElectricityEmission = findViewById(R.id.tvMonthlyElectricityEmission);
        tvFoodCarbonEmission = findViewById(R.id.tvFoodCarbonEmission);
        tvHeatingCarbonEmission = findViewById(R.id.tvHeatingCarbonEmission);
        tvTotalResult = findViewById(R.id.tvTotalResult);
        tvCarEmissionPercentage = findViewById(R.id.tvCarEmissionPercentage);
        tvPublicTransportEmissionPercentage = findViewById(R.id.tvPublicTransportEmissionPercentage);
        tvElectricEmissionPercentage = findViewById(R.id.tvElectricEmissionPercentage);
        tvFoodEmissionPercentage = findViewById(R.id.tvFoodEmissionPercentage);
        tvElectricityEmissionPercentage = findViewById(R.id.tvElectricityEmissionPercentage);
        tvHeatingEmissionPercentage = findViewById(R.id.tvHeatingEmissionPercentage);

        // Get CarbonEmissionData from intent
        CarbonEmissionData carbonEmissionData = (CarbonEmissionData) getIntent().getSerializableExtra("carbonEmissionData");

        // Display the results
        if (carbonEmissionData != null) {
            double monthlyElectricityEmission = carbonEmissionData.getMonthlyPerCapitaElectricityCarbonEmission();
            double foodCarbonEmission = carbonEmissionData.getFoodCarbonEmission() / 12 / carbonEmissionData.getFamilyMembers();
            double heatingCarbonEmission = carbonEmissionData.getMonthlyPerCapitaHeatingCarbonEmission();
            double totalEmission = carbonEmissionData.getTotalCarbonEmission() / 12 / carbonEmissionData.getFamilyMembers();
            double carEmissionPercentage = carbonEmissionData.getCarEmissionPercentage();
            double publicTransportEmissionPercentage = carbonEmissionData.getPublicTransportEmissionPercentage();
            double electricEmissionPercentage = carbonEmissionData.getElectricEmissionPercentage();
            double foodEmissionPercentage = carbonEmissionData.getFoodEmissionPercentage();
            double electricityEmissionPercentage = carbonEmissionData.getElectricityEmissionPercentage();
            double heatingEmissionPercentage = carbonEmissionData.getHeatingEmissionPercentage();

            tvElectricityResult.setText("Electricity CO2 Emission: " + monthlyElectricityEmission + " kg CO2 per month");
            tvMonthlyElectricityEmission.setText("Monthly Per Capita Electricity Emission: " + monthlyElectricityEmission + " kg CO2");
            tvFoodCarbonEmission.setText("Food CO2 Emission: " + foodCarbonEmission + " kg CO2 per month");
            tvHeatingCarbonEmission.setText("Heating CO2 Emission: " + heatingCarbonEmission + " kg CO2 per month");
            tvTotalResult.setText("Total CO2 Emission: " + totalEmission + " kg CO2 per month");
            tvCarEmissionPercentage.setText("Car Emission Percentage: " + carEmissionPercentage + " %");
            tvPublicTransportEmissionPercentage.setText("Public Transport Emission Percentage: " + publicTransportEmissionPercentage + " %");
            tvElectricEmissionPercentage.setText("Electric Transport Emission Percentage: " + electricEmissionPercentage + " %");
            tvFoodEmissionPercentage.setText("Food Emission Percentage: " + foodEmissionPercentage + " %");
            tvElectricityEmissionPercentage.setText("Electricity Emission Percentage: " + electricityEmissionPercentage + " %");
            tvHeatingEmissionPercentage.setText("Heating Emission Percentage: " + heatingEmissionPercentage + " %");
        }
    }
}
