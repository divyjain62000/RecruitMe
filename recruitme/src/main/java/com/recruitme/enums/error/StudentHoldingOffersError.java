package com.recruitme.enums.error;

public enum StudentHoldingOffersError {

    OFFER_DETAIL_REQUIRED("Offer details required"),
    SALARY_REQUIRED("Salary required"),
    INVALID_SALARY("Invalid salary"),
    STUDENT_ALREADY_ADDED("You cannot add this student again edit his/her detail"),
    COMPANY_NAME_REQUIRED("Company name required"),
    JOB_TYPE_REQUIRED("Job type required");

    private String studentHoldingOfferErr;
    StudentHoldingOffersError(String studentHoldingOfferErr) { this.studentHoldingOfferErr = studentHoldingOfferErr; }
    public String getStudentHoldingOfferErr() { return this.studentHoldingOfferErr; }
}
