package com.recruitme.enums.error;

public enum StateError {

    STATE_REQURED("State required"),
    INVALID_STATE_ID("Invalid state");

    private String stateError;
    StateError(String stateError) { this.stateError=stateError; }
    public String getStateError() { return this.stateError; }
}


