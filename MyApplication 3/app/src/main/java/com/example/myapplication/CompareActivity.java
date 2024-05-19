package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        barChart = findViewById(R.id.barChart);

        CarbonEmissionData carbonEmissionData = CarbonEmissionDataHolder.getInstance().getCarbonEmissionData();

        // User values
        ArrayList<BarEntry> userValues = new ArrayList<>();
        userValues.add(new BarEntry(0f, (float) carbonEmissionData.getHeatingCarbonEmission())); // Home
        userValues.add(new BarEntry(1f, (float) carbonEmissionData.getFoodCarbonEmission())); // Food
        userValues.add(new BarEntry(2f, (float) (carbonEmissionData.getCarCarbonEmission() + carbonEmissionData.getPublicTransportCarbonEmission() + carbonEmissionData.getElectricCarbonEmission()))); // Travel

        // Finnish average values
        ArrayList<BarEntry> finlandValues = new ArrayList<>();
        finlandValues.add(new BarEntry(0f, 2900f / 12f)); // Home - example value, replace with actual data
        finlandValues.add(new BarEntry(1f, 2000f / 12f)); // Food - example value, replace with actual data
        finlandValues.add(new BarEntry(2f, 1500f / 12f)); // Travel - example value, replace with actual data

        BarDataSet userDataSet = new BarDataSet(userValues, "Your result");
        userDataSet.setColor(getResources().getColor(R.color.colorPrimary)); // User color

        BarDataSet finlandDataSet = new BarDataSet(finlandValues, "Finnish average");
        finlandDataSet.setColor(getResources().getColor(R.color.colorAccent)); // Finnish average color

        BarData barData = new BarData(userDataSet, finlandDataSet);
        barChart.setData(barData);
        barChart.groupBars(0f, 0.4f, 0.1f); // Group the bars together

        Legend legend = barChart.getLegend();
        legend.setEnabled(true);

        barChart.invalidate();
    }
}
