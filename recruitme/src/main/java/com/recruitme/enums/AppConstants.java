package com.recruitme.enums;

public enum AppConstants {
    DASH("-"),
    SPACE(" "),
    UNDERSCORE("_"),
    ;

    private String appConstants;
    AppConstants(String appConstants) { this.appConstants=appConstants; }
    public String getAppConstants() { return this.appConstants; }
}
