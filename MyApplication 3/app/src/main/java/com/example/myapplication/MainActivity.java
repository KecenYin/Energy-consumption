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

    private EditText etFamilyMembers, etHouseArea, etFossilFuelPercentage, etRenewablePercentage;
    private Spinner spinnerHouseType, spinnerPowerType;
    private Switch swElectricHeating;
    private String houseType, powerType;
    private int familyMembers;
    private double houseArea, fossilFuelPercentage, renewablePercentage, greenElectricityPercentage;
    private boolean electricHeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化UI组件
        etFamilyMembers = findViewById(R.id.etFamilyMembers);
        etHouseArea = findViewById(R.id.etHouseArea);
        etFossilFuelPercentage = findViewById(R.id.etFossilFuelPercentage);
        etRenewablePercentage = findViewById(R.id.etRenewablePercentage);
        spinnerHouseType = findViewById(R.id.spinnerHouseType);
        spinnerPowerType = findViewById(R.id.spinnerPowerType);
        swElectricHeating = findViewById(R.id.swElectricHeating);
        Button nextButton = findViewById(R.id.btnNext);

        // 设置房屋类型下拉框
        ArrayAdapter<CharSequence> adapterHouseType = ArrayAdapter.createFromResource(this,
                R.array.house_type_array, android.R.layout.simple_spinner_item);
        adapterHouseType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHouseType.setAdapter(adapterHouseType);

        // 设置电力类型下拉框
        ArrayAdapter<CharSequence> adapterPowerType = ArrayAdapter.createFromResource(this,
                R.array.power_types_array, android.R.layout.simple_spinner_item);
        adapterPowerType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPowerType.setAdapter(adapterPowerType);

        // 根据电力类型选择设置可见性
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

        // 设置下一步按钮点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectInputData();
                double electricityConsumption = calculateElectricityConsumption();
                CarbonEmissionData carbonEmissionData = new CarbonEmissionData.Builder()
                        .setFamilyMembers(familyMembers)
                        .setHouseArea(houseArea)
                        .setElectricityConsumption(electricityConsumption)
                        .setGreenElectricityPercentage(greenElectricityPercentage)
                        .build();

                CarbonEmissionDataHolder.getInstance().setCarbonEmissionData(carbonEmissionData);

                Intent intent = new Intent(MainActivity.this, test.class);
                startActivity(intent);
            }
        });
    }

    // 收集输入数据
    private void collectInputData() {
        familyMembers = Integer.parseInt(etFamilyMembers.getText().toString());
        houseArea = Double.parseDouble(etHouseArea.getText().toString());
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

    // 计算电力消耗
    private double calculateElectricityConsumption() {
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
}
