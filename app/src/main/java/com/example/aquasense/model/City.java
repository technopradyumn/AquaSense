package com.example.aquasense.model;

public class City {
    private String cityId;
    private String name;
    private String imageResource;
    private String waterQuality;

    private String waterQualityStatus;

    public City() {
        // Default constructor required for Firebase deserialization

    }

    public City(String cityId, String name, String imageResource, String waterQuality, String waterQualityStatus) {
        this.cityId = cityId;
        this.name = name;
        this.imageResource = imageResource;
        this.waterQuality = waterQuality;
        this.waterQualityStatus = waterQualityStatus;
    }

    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getWaterQuality() {
        return waterQuality;
    }

    public String getWaterQualityStatus() {
        return waterQualityStatus;
    }
}