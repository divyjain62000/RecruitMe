package com.recruitme.enums.error;

public enum CityError {

    CITY_REQUIRED("City requried"),
    INVALID_CITY_ID("Invalid city");

    private String cityError;
    CityError(String cityError) { this.cityError=cityError; }
    public String getCityError() { return this.cityError; }

}
