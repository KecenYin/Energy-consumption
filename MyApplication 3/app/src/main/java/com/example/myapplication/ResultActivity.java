package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView tvElectricityResult, tvFoodCarbonEmission, tvHeatingCarbonEmission,
            tvTotalResult, tvCarEmissionPercentage, tvBusEmissionPercentage, tvElectricEmissionPercentage,
            tvFoodEmissionPercentage, tvElectricityEmissionPercentage, tvHeatingEmissionPercentage, tvTransportEmissionPercentage;
    private Button btnViewAnalysis, btnCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 初始化UI组件
        tvElectricityResult = findViewById(R.id.tvElectricityResult);
        tvFoodCarbonEmission = findViewById(R.id.tvFoodCarbonEmission);
        tvHeatingCarbonEmission = findViewById(R.id.tvHeatingCarbonEmission);
        tvTotalResult = findViewById(R.id.tvTotalResult);
        tvCarEmissionPercentage = findViewById(R.id.tvCarEmissionPercentage);
        tvBusEmissionPercentage = findViewById(R.id.tvBusEmissionPercentage);
        tvElectricEmissionPercentage = findViewById(R.id.tvElectricEmissionPercentage);
        tvFoodEmissionPercentage = findViewById(R.id.tvFoodEmissionPercentage);
        tvElectricityEmissionPercentage = findViewById(R.id.tvElectricityEmissionPercentage);
        tvHeatingEmissionPercentage = findViewById(R.id.tvHeatingEmissionPercentage);
        tvTransportEmissionPercentage = findViewById(R.id.tvTransportEmissionPercentage);
        btnViewAnalysis = findViewById(R.id.btnViewAnalysis);
        btnCompare = findViewById(R.id.btnCompare);

        // 从 CarbonEmissionDataHolder 中获取 CarbonEmissionData 对象
        CarbonEmissionData carbonEmissionData = CarbonEmissionDataHolder.getInstance().getCarbonEmissionData();

        // 显示结果
        if (carbonEmissionData != null) {
            double monthlyElectricityEmission = carbonEmissionData.getMonthlyPerCapitaElectricityCarbonEmission();
            double foodCarbonEmission = carbonEmissionData.getFoodCarbonEmission();
            double heatingCarbonEmission = carbonEmissionData.getMonthlyPerCapitaHeatingCarbonEmission();
            double totalEmission = carbonEmissionData.getTotalMonthlyCarbonEmission();
            double carEmissionPercentage = carbonEmissionData.getCarEmissionPercentage();
            double busEmissionPercentage = carbonEmissionData.getPublicTransportEmissionPercentage();
            double electricEmissionPercentage = carbonEmissionData.getElectricEmissionPercentage();
            double foodEmissionPercentage = carbonEmissionData.getFoodEmissionPercentage();
            double electricityEmissionPercentage = carbonEmissionData.getElectricityEmissionPercentage();
            double heatingEmissionPercentage = carbonEmissionData.getHeatingEmissionPercentage();
            double transportEmissionPercentage = carEmissionPercentage + busEmissionPercentage + electricEmissionPercentage;

            tvElectricityResult.setText("Electricity CO2 Emission: " + monthlyElectricityEmission + " kg CO2 per month");
            tvFoodCarbonEmission.setText("Food CO2 Emission: " + foodCarbonEmission + " kg CO2 per month");
            tvHeatingCarbonEmission.setText("Heating CO2 Emission: " + heatingCarbonEmission + " kg CO2 per month");
            tvTotalResult.setText("Total CO2 Emission: " + totalEmission + " kg CO2 per month");
            tvCarEmissionPercentage.setText("Car Emission Percentage: " + carEmissionPercentage + " %");
            tvBusEmissionPercentage.setText("Bus Emission Percentage: " + busEmissionPercentage + " %");
            tvElectricEmissionPercentage.setText("Electric Transport Emission Percentage: " + electricEmissionPercentage + " %");
            tvFoodEmissionPercentage.setText("Food Emission Percentage: " + foodEmissionPercentage + " %");
            tvElectricityEmissionPercentage.setText("Electricity Emission Percentage: " + electricityEmissionPercentage + " %");
            tvHeatingEmissionPercentage.setText("Heating Emission Percentage: " + heatingEmissionPercentage + " %");
            tvTransportEmissionPercentage.setText("Total Transport Emission Percentage: " + transportEmissionPercentage + " %");
        }

        btnViewAnalysis.setOnClickListener(v -> showAnalysisDialog(carbonEmissionData));

        btnCompare.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, CompareActivity.class);
            startActivity(intent);
        });
    }

    private void showAnalysisDialog(CarbonEmissionData carbonEmissionData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Carbon Emission Analysis");

        // 使用布局文件创建View
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_analysis, null);
        builder.setView(dialogView);

        PieChart pieChart = dialogView.findViewById(R.id.pieChart);

        double totalEmission = carbonEmissionData.getTotalMonthlyCarbonEmission();
        double foodEmission = carbonEmissionData.getFoodCarbonEmission();
        double transportEmission = carbonEmissionData.getCarCarbonEmission() + carbonEmissionData.getPublicTransportCarbonEmission() + carbonEmissionData.getElectricCarbonEmission();
        double electricityEmission = carbonEmissionData.getMonthlyPerCapitaElectricityCarbonEmission();
        double heatingEmission = carbonEmissionData.getMonthlyPerCapitaHeatingCarbonEmission();

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (foodEmission / totalEmission * 100), "Food"));
        entries.add(new PieEntry((float) (transportEmission / totalEmission * 100), "Transport"));
        entries.add(new PieEntry((float) (electricityEmission / totalEmission * 100), "Electricity"));
        entries.add(new PieEntry((float) (heatingEmission / totalEmission * 100), "Heating"));

        PieDataSet dataSet = new PieDataSet(entries, "Emission Sources");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f); // 设置百分比字体大小

        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        // 调整图例字体大小
        pieChart.getLegend().setTextSize(14f);

        // 添加每个部分的CO2排放量
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Food: ").append(foodEmission).append(" kg CO2\n");
        descriptionBuilder.append("Transport: ").append(transportEmission).append(" kg CO2\n");
        descriptionBuilder.append("Electricity: ").append(electricityEmission).append(" kg CO2\n");
        descriptionBuilder.append("Heating: ").append(heatingEmission).append(" kg CO2\n");

        TextView descriptionLabel = dialogView.findViewById(R.id.descriptionLabel);
        descriptionLabel.setText(descriptionBuilder.toString());
        descriptionLabel.setTextSize(16f); // 设置描述字体大小

        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
