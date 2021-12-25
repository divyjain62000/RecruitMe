package com.recruitme.enums.error;

/**
 * StudentError holds error related to Student
 *
 * @author Divy Jain
 * @version 1.0
 * @since Sept 03,2021 06:55:00 AM
 */
public enum StudentError {

    STUDENT_DETAIL_REQUIRED("Student details required"),
    STUDENT_ID_REQUIRED("Student id required"),
    ENROLLMENT_REQUIRED("Enrollment Number required"),
    INVALID_STUDENT_ID("Invalid student id"),
    INVALID_ENROLLMENT_NUMBER("Invalid enrollment number"),
    ENROLLMENT_EXISTS("Enrollment number exists"),
    MOU_COMAPANY_REFERENCE_ID_REQUIRED("MOU Company reference id"),
    MOU_COMAPANY_REFERENCE_ID_EXISTS("MOU Company reference id exists"),
    NATIONALITY_REQUIRED("Please select your nationality"),
    CRIMINAL_CASE_DETAIL_REQUIRED("Give criminal case detail if you have any"),
    DISABILITY_DETAIL_REQUIRED("Give disability detail if you have any"),
    BLACKLIST_REASON_REQUIRED("Blacklist reason required");

    private String studentError;
    StudentError(String studentError) { this.studentError=studentError; }
    public String getStudentError() { return this.studentError; }
}
