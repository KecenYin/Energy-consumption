package com.example.myapplication;

public class CarbonEmissionDataHolder {
    private static final CarbonEmissionDataHolder instance = new CarbonEmissionDataHolder();
    private CarbonEmissionData carbonEmissionData;

    private CarbonEmissionDataHolder() {
    }

    public static CarbonEmissionDataHolder getInstance() {
        return instance;
    }

    public CarbonEmissionData getCarbonEmissionData() {
        return carbonEmissionData;
    }

    public void setCarbonEmissionData(CarbonEmissionData carbonEmissionData) {
        this.carbonEmissionData = carbonEmissionData;
    }
}
