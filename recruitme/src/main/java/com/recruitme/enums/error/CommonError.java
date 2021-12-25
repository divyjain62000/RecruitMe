package com.recruitme.enums.error;

/**
 * CommonError holds errors which are common
 *
 * @author Divy Jain
 * @version 1.0
 * @since Sept 03,2021 06:42:00 AM
 */
public enum CommonError {

    NEED_ALL_DETAIL("Required all details"),
    INVALID_ID("Invalid id"),
    ID_REQUIRED("ID required"),
    NAME_REQUIRED("Name required"),
    PARENTS_DETAIL_REQUIRED("Parents detail required"),
    FATHER_NAME_REQUIRED("Father name required"),
    MOTHER_NAME_REQUIRED("Mother name required"),
    PASSWORD_REQUIRED("Password required"),
    PASSWORD_LENGTH_EXCEED("Password must not be greater than 25"),
    PRIMARY_MOBILE_NUMBER_REQUIRED("Primary mobile number required"),
    SECONDARY_MOBILE_NUMBER_REQUIRED("Secondary mobile number required"),
    INVALID_MOBILE_NUMBER("Invalid mobile number"),
    PRIMARY_EMAIL_REQUIRED("Primary email-id required"),
    SECONDARY_EMAIL_REQUIRED("Secondary email-id required"),
    INVALID_EMAIL_ID("Invalid email-id"),
    EMAIL_ID_EXISTS("Email-id already exists"),
    GENDER_REQUIRED("Gender required"),
    LENGTH_EXCEED("Length should be less than or equal to "),
    MOBILE_NUMBER_REQUIRED("Mobile number required"),
    MOBILE_NUMBER_EXISTS("Mobile number already exists"),
    ROLE_REQUIRED("User role required");

    private String commonError;
    CommonError(String commonError) { this.commonError=commonError; }
    public String getCommonError() { return this.commonError; }
}
