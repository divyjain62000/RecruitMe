package com.recruitme.enums.error;

/**
 * AddressError holds error related to Address
 *
 * @author Divy Jain
 * @version 1.0
 * @since Sept 03,2021 06:38:00 AM
 */
public enum AddressError {

    ADDRESS_DETAIL_REQURIED("Address details required"),
    ADDRESS_LINE1_REQUIRED("Address line 1 required"),
    PIN_CODE_REQUIRED("PIN Code requried");

    private String addressError;
    AddressError(String addressError) { this.addressError=addressError; }
    public String getAddressError() { return this.addressError; }
}
