package com.recruitme.enums.error;

import com.recruitme.domain.Country;

public enum CountryError {

    COUNTRY_REQURED("Country required"),
    INVALID_COUNTRY_ID("Invalid country");

    private String countryError;
    CountryError(String countryError) { this.countryError=countryError; }
    public String getCountryError() { return this.countryError; }
}


