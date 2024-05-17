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
    private double additionalHouseArea;

    private static final double AVERAGE_ELECTRICITY_EMISSION_FACTOR = 0.281; // kg CO2eq/kWh
    private static final double GREEN_ELECTRICITY_EMISSION_FACTOR = 0.020; // kg CO2eq/kWh
    private static final double HEATING_ENERGY_CONSUMPTION_FACTOR = 241; // kWh/m2 per year
    private static final double HEATING_EMISSION_FACTOR = 0.267; // kg CO2eq/kWh

    public CarbonEmissionData(int familyMembers, double houseArea, double additionalHouseArea, double electricityConsumption,
                              double greenElectricityPercentage, double carDistance, double bicycleDistance,
                              double publicTransportDistance, double electricTransportDistance, double foodIntake,
                              double meatPercentage, double foodCarbonEmission, double carCarbonEmission,
                              double publicTransportCarbonEmission, double electricCarbonEmission) {
        this.familyMembers = familyMembers;
        this.houseArea = houseArea;
        this.additionalHouseArea = additionalHouseArea;
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
        return heatingCarbonEmission;
    }

    public double getHouseArea() {
        return houseArea;
    }

    public double getAdditionalHouseArea() {
        return additionalHouseArea;
    }

    public double getTotalHouseArea() {
        return houseArea + additionalHouseArea;
    }

    private double calculateElectricityCarbonEmission() {
        double averageElectricityConsumption = electricityConsumption * ((100 - greenElectricityPercentage) / 100);
        double greenElectricityConsumption = electricityConsumption * (greenElectricityPercentage / 100);
        return (averageElectricityConsumption * AVERAGE_ELECTRICITY_EMISSION_FACTOR) +
                (greenElectricityConsumption * GREEN_ELECTRICITY_EMISSION_FACTOR);
    }

    private double calculateHeatingCarbonEmission() {
        double totalHeatingEnergyConsumption = getTotalHouseArea() * HEATING_ENERGY_CONSUMPTION_FACTOR;
        return totalHeatingEnergyConsumption * HEATING_EMISSION_FACTOR;
    }

    public double getPerCapitaElectricityCarbonEmission() {
        return electricityCarbonEmission / familyMembers;
    }

    public double getMonthlyPerCapitaElectricityCarbonEmission() {
        return getPerCapitaElectricityCarbonEmission() / 12;
    }

    public double getMonthlyPerCapitaHeatingCarbonEmission() {
        return heatingCarbonEmission / 12 / familyMembers;
    }

    public double getTotalCarbonEmission() {
        return electricityCarbonEmission + foodCarbonEmission + carCarbonEmission +
                publicTransportCarbonEmission + electricCarbonEmission + heatingCarbonEmission;
    }

    public double getElectricityEmissionPercentage() {
        return (electricityCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public double getCarEmissionPercentage() {
        return (carCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public double getPublicTransportEmissionPercentage() {
        return (publicTransportCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public double getFoodEmissionPercentage() {
        return (foodCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public double getElectricEmissionPercentage() {
        return (electricCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public double getHeatingEmissionPercentage() {
        return (heatingCarbonEmission / getTotalCarbonEmission()) * 100;
    }

    public static class Builder {
        private int familyMembers;
        private double houseArea;
        private double additionalHouseArea;
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

        public Builder setFamilyMembers(int familyMembers) {
            this.familyMembers = familyMembers;
            return this;
        }

        public Builder setHouseArea(double houseArea) {
            this.houseArea = houseArea;
            return this;
        }

        public Builder setAdditionalHouseArea(double additionalHouseArea) {
            this.additionalHouseArea = additionalHouseArea;
            return this;
        }

        public Builder setElectricityConsumption(double electricityConsumption) {
            this.electricityConsumption = electricityConsumption;
            return this;
        }

        public Builder setGreenElectricityPercentage(double greenElectricityPercentage) {
            this.greenElectricityPercentage = greenElectricityPercentage;
            return this;
        }

        public Builder setCarDistance(double carDistance) {
            this.carDistance = carDistance;
            return this;
        }

        public Builder setBicycleDistance(double bicycleDistance) {
            this.bicycleDistance = bicycleDistance;
            return this;
        }

        public Builder setPublicTransportDistance(double publicTransportDistance) {
            this.publicTransportDistance = publicTransportDistance;
            return this;
        }

        public Builder setElectricTransportDistance(double electricTransportDistance) {
            this.electricTransportDistance = electricTransportDistance;
            return this;
        }

        public Builder setFoodIntake(double foodIntake) {
            this.foodIntake = foodIntake;
            return this;
        }

        public Builder setMeatPercentage(double meatPercentage) {
            this.meatPercentage = meatPercentage;
            return this;
        }

        public Builder setFoodCarbonEmission(double foodCarbonEmission) {
            this.foodCarbonEmission = foodCarbonEmission;
            return this;
        }

        public Builder setCarCarbonEmission(double carCarbonEmission) {
            this.carCarbonEmission = carCarbonEmission;
            return this;
        }

        public Builder setPublicTransportCarbonEmission(double publicTransportCarbonEmission) {
            this.publicTransportCarbonEmission = publicTransportCarbonEmission;
            return this;
        }

        public Builder setElectricCarbonEmission(double electricCarbonEmission) {
            this.electricCarbonEmission = electricCarbonEmission;
            return this;
        }

        public CarbonEmissionData build() {
            return new CarbonEmissionData(familyMembers, houseArea, additionalHouseArea, electricityConsumption,
                    greenElectricityPercentage, carDistance, bicycleDistance, publicTransportDistance,
                    electricTransportDistance, foodIntake, meatPercentage, foodCarbonEmission, carCarbonEmission,
                    publicTransportCarbonEmission, electricCarbonEmission);
        }
    }
}
