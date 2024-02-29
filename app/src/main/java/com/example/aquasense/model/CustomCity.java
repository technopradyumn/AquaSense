package com.example.aquasense.model;

public class CustomCity {
    private String cityId;
    private String name;
    private String imageResource;
    private String pH;
    private String dissolvedOxygen;
    private String temperature;
    private String turbidity;
    private String chlorides;

    public CustomCity() {

    }

    public CustomCity(String cityId, String name, String imageResource, String pH, String dissolvedOxygen, String temperature, String turbidity, String chlorides) {
        this.cityId = cityId;
        this.name = name;
        this.imageResource = imageResource;
        this.pH = pH;
        this.dissolvedOxygen = dissolvedOxygen;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.chlorides = chlorides;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    public String getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public void setDissolvedOxygen(String dissolvedOxygen) {
        this.dissolvedOxygen = dissolvedOxygen;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(String turbidity) {
        this.turbidity = turbidity;
    }

    public String getChlorides() {
        return chlorides;
    }

    public void setChlorides(String chlorides) {
        this.chlorides = chlorides;
    }
}