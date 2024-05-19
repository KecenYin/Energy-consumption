package com.example.myapplication;

import java.io.Serializable;

public class CarbonEmissionData implements Serializable {
    private int familyMembers;
    private double electricityConsumption;
    private double electricityCarbonEmission;
    private double greenElectricityPercentage;
    private double carDistance;
    private double bicycleDistance;
    private double publicTransportDistance;
    private double electricTransportDistance;
    private double foodIntake;
    private double meatPercentage;
    private double foodCarbonEmission;
    private double carCarbonEmission;
    private double publicTransportCarbonEmission;
    private double electricCarbonEmission;
    private double heatingCarbonEmission;
    private double houseArea;

    private static final double AVERAGE_ELECTRICITY_EMISSION_FACTOR = 0.281; // kg CO2eq/kWh
    private static final double GREEN_ELECTRICITY_EMISSION_FACTOR = 0.020; // kg CO2eq/kWh
    private static final double HEATING_ENERGY_CONSUMPTION_FACTOR = 241; // kWh/m2 per year
    private static final double HEATING_EMISSION_FACTOR = 0.267; // kg CO2eq/kWh

    public CarbonEmissionData(int familyMembers, double houseArea, double electricityConsumption,
                              double greenElectricityPercentage, double carDistance, double bicycleDistance,
                              double publicTransportDistance, double electricTransportDistance, double foodIntake,
                              double meatPercentage, double foodCarbonEmission, double carCarbonEmission,
                              double publicTransportCarbonEmission, double electricCarbonEmission) {
        this.familyMembers = familyMembers;
        this.houseArea = houseArea;
        this.electricityConsumption = electricityConsumption;
        this.greenElectricityPercentage = greenElectricityPercentage;
        this.carDistance = carDistance;
        this.bicycleDistance = bicycleDistance;
        this.publicTransportDistance = publicTransportDistance;
        this.electricTransportDistance = electricTransportDistance;
        this.foodIntake = foodIntake;
        this.meatPercentage = meatPercentage;
        this.foodCarbonEmission = foodCarbonEmission;
        this.carCarbonEmission = carCarbonEmission;
        this.publicTransportCarbonEmission = publicTransportCarbonEmission;
        this.electricCarbonEmission = electricCarbonEmission;
        this.electricityCarbonEmission = calculateElectricityCarbonEmission();
        this.heatingCarbonEmission = calculateHeatingCarbonEmission();
    }

    public int getFamilyMembers() {
        return familyMembers;
    }

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public double getElectricityCarbonEmission() {
        return electricityCarbonEmission;
    }

    public double getGreenElectricityPercentage() {
        return greenElectricityPercentage;
    }

    public double getCarDistance() {
        return carDistance;
    }

    public double getBicycleDistance() {
        return bicycleDistance;
    }

    public double getPublicTransportDistance() {
        return publicTransportDistance;
    }

    public double getElectricTransportDistance() {
        return electricTransportDistance;
    }

    public double getFoodIntake() {
        return foodIntake;
    }

    public double getMeatPercentage() {
        return meatPercentage;
    }

    public double getFoodCarbonEmission() {
        return foodCarbonEmission;
    }

    public double getCarCarbonEmission() {
        return carCarbonEmission;
    }

    public double getPublicTransportCarbonEmission() {
        return publicTransportCarbonEmission;
    }

    public double getElectricCarbonEmission() {
        return electricCarbonEmission;
    }

    public double getHeatingCarbonEmission() {
        return heatingCarbonEmission / 12 / familyMembers; // 每月每人均摊
    }

    public double getHouseArea() {
        return houseArea;
    }

    // 计算电力碳排放
    private double calculateElectricityCarbonEmission() {
        double averageElectricityConsumption = electricityConsumption * ((100 - greenElectricityPercentage) / 100);
        double greenElectricityConsumption = electricityConsumption * (greenElectricityPercentage / 100);
        return (averageElectricityConsumption * AVERAGE_ELECTRICITY_EMISSION_FACTOR) +
                (greenElectricityConsumption * GREEN_ELECTRICITY_EMISSION_FACTOR);
    }

    // 计算供暖碳排放
    private double calculateHeatingCarbonEmission() {
        double totalHeatingEnergyConsumption = houseArea * HEATING_ENERGY_CONSUMPTION_FACTOR;
        return totalHeatingEnergyConsumption * HEATING_EMISSION_FACTOR;
    }

    // 获取每月每人电力碳排放
    public double getMonthlyPerCapitaElectricityCarbonEmission() {
        return electricityCarbonEmission / 12 / familyMembers;
    }

    // 获取总月度碳排放
    public double getTotalMonthlyCarbonEmission() {
        return getMonthlyPerCapitaElectricityCarbonEmission() + getHeatingCarbonEmission() + foodCarbonEmission +
                carCarbonEmission + publicTransportCarbonEmission + electricCarbonEmission;
    }

    // 获取电力排放占比
    public double getElectricityEmissionPercentage() {
        return (getMonthlyPerCapitaElectricityCarbonEmission() / getTotalMonthlyCarbonEmission()) * 100;
    }

    // 获取汽车排放占比
    public double getCarEmissionPercentage() {
        return (carCarbonEmission / getTotalMonthlyCarbonEmission()) * 100;
    }

    // 获取公共交通排放占比
    public double getPublicTransportEmissionPercentage() {
        return (publicTransportCarbonEmission / getTotalMonthlyCarbonEmission()) * 100;
    }

    // 获取食物排放占比
    public double getFoodEmissionPercentage() {
        return (foodCarbonEmission / getTotalMonthlyCarbonEmission()) * 100;
    }

    // 获取电动交通排放占比
    public double getElectricEmissionPercentage() {
        return (electricCarbonEmission / getTotalMonthlyCarbonEmission()) * 100;
    }

    // 获取供暖排放占比
    public double getHeatingEmissionPercentage() {
        return (getHeatingCarbonEmission() / getTotalMonthlyCarbonEmission()) * 100;
    }

    public static class Builder {
        private int familyMembers;
        private double houseArea;
        private double electricityConsumption;
        private double greenElectricityPercentage;
        private double carDistance;
        private double bicycleDistance;
        private double publicTransportDistance;
        private double electricTransportDistance;
        private double foodIntake;
        private double meatPercentage;
        private double foodCarbonEmission;
        private double carCarbonEmission;
        private double publicTransportCarbonEmission;
        private double electricCarbonEmission;

        // 设置家庭成员数
        public Builder setFamilyMembers(int familyMembers) {
            this.familyMembers = familyMembers;
            return this;
        }

        // 设置房屋面积
        public Builder setHouseArea(double houseArea) {
            this.houseArea = houseArea;
            return this;
        }

        // 设置电力消耗
        public Builder setElectricityConsumption(double electricityConsumption) {
            this.electricityConsumption = electricityConsumption;
            return this;
        }

        // 设置绿色电力百分比
        public Builder setGreenElectricityPercentage(double greenElectricityPercentage) {
            this.greenElectricityPercentage = greenElectricityPercentage;
            return this;
        }

        // 设置汽车距离
        public Builder setCarDistance(double carDistance) {
            this.carDistance = carDistance;
            return this;
        }

        // 设置自行车距离
        public Builder setBicycleDistance(double bicycleDistance) {
            this.bicycleDistance = bicycleDistance;
            return this;
        }

        // 设置公共交通距离
        public Builder setPublicTransportDistance(double publicTransportDistance) {
            this.publicTransportDistance = publicTransportDistance;
            return this;
        }

        // 设置电动交通距离
        public Builder setElectricTransportDistance(double electricTransportDistance) {
            this.electricTransportDistance = electricTransportDistance;
            return this;
        }

        // 设置食物摄入
        public Builder setFoodIntake(double foodIntake) {
            this.foodIntake = foodIntake;
            return this;
        }

        // 设置肉类百分比
        public Builder setMeatPercentage(double meatPercentage) {
            this.meatPercentage = meatPercentage;
            return this;
        }

        // 设置食物碳排放
        public Builder setFoodCarbonEmission(double foodCarbonEmission) {
            this.foodCarbonEmission = foodCarbonEmission;
            return this;
        }

        // 设置汽车碳排放
        public Builder setCarCarbonEmission(double carCarbonEmission) {
            this.carCarbonEmission = carCarbonEmission;
            return this;
        }

        // 设置公共交通碳排放
        public Builder setPublicTransportCarbonEmission(double publicTransportCarbonEmission) {
            this.publicTransportCarbonEmission = publicTransportCarbonEmission;
            return this;
        }

        // 设置电动交通碳排放
        public Builder setElectricCarbonEmission(double electricCarbonEmission) {
            this.electricCarbonEmission = electricCarbonEmission;
            return this;
        }

        // 构建CarbonEmissionData对象
        public CarbonEmissionData build() {
            return new CarbonEmissionData(familyMembers, houseArea, electricityConsumption,
                    greenElectricityPercentage, carDistance, bicycleDistance, publicTransportDistance,
                    electricTransportDistance, foodIntake, meatPercentage, foodCarbonEmission, carCarbonEmission,
                    publicTransportCarbonEmission, electricCarbonEmission);
        }
    }
}
