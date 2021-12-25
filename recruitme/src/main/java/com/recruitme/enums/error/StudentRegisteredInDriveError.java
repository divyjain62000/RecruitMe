package com.recruitme.enums.error;

public enum StudentRegisteredInDriveError {

    PROGRAM_NOT_ALLOW("Hiring is not available for your program"),
    BRANCH_NOT_ALLOW("Hiring is not available for your branch"),
    BELOW_REQUIRED_MARKS_10th("Your 10th marks is less than minimum required marks"),
    BELOW_REQUIRED_MARKS_12th("Your 12th marks is less than minimum required marks"),
    BELOW_REQUIRED_MARKS_UG("Your UG marks is less than minimum required marks"),
    BELOW_REQUIRED_MARKS_PG("Your PG marks is less than minimum required marks"),
    ONLY_FOR_GIRLS("Hiring is only for girls"),
    ONLY_FOR_BOYS("Hiring is only for boys"),
    ONLY_FOR_PG_STUDENTS("Hiring is only for PG students"),
    ONLY_FOR_UG_STUDENTS("Hiring is only for UG students"),
    MORE_MINIMUM_BACKLOGS_THAN_ALLOWED("You have more number of backlogs than minimum backlogs allowed"),
    REQUIRED_10th_PASS("Candidate should pass in 10th class"),
    REQUIRED_12th_PASS("Candidate should pass in 12th class"),
    UG_MARKS_DETAIL_REQUIRED("Candidate should pusrsuing UG course or should complete UG course"),
    PG_MARKS_DETAIL_REQUIRED("Candidate should pusrsuing PG course or should complete PG course"),
    PG_REQUIRED("PG degree required"),
    STUDETNT_ALREADY_REGISTERD("You already registered in this drive"),
    SEARCH_CRETERIA_NOT_SELECTED("You have not select all search criteria")
    ;

    private String studentRegisteredInDrive;
    StudentRegisteredInDriveError(String studentRegisteredInDrive) { this.studentRegisteredInDrive=studentRegisteredInDrive; }
    public String getStudentRegisteredInDrive() { return this.studentRegisteredInDrive; }
}
