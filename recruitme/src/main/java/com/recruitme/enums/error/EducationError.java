package com.recruitme.enums.error;

public enum EducationError {

    EDUCATION_DETAIL_REQUIRED("Education details required"),
    COLLEGE_NAME_REQUIRED("College name required"),
    UG_CGPA_REQURIED("UG CGPA required"),
    UG_PERCENTAGE_REQURIED("UG percentage required"),
    PG_CGPA_REQURIED("PG CGPA required"),
    PG_PERCENTAGE_REQURIED("PG percentage required"),
    SCHOOL_NAME_REQUIRED("10th school name required"),
    SCHOOL_BOARD_REQUIRED("School board required"),
    SENIOR_SECONDARY_SCHOOL_CGPA_REQUIRED("10th CGPA required"),
    SENIOR_SECONDARY_SCHOOL_PERCENTAGE_REQUIRED("10th percentage required"),
    HIGH_SCHOOL_PERCENTAGE_REQUIRED("12th percentage required"),
    MARKS_TYPE_REQUIRED("Senior secondary school marks type required"),
    NA_NOT_APPLICABLE_FOR_MARKS_TYPE("Marks type required NA not applicable"),
    INVALID_SCHOOL_BOARD("Invalid school board"),
    INVALID_MARKS("Invalid marks"),
    PASSOUT_YEAR_REQUIRED("Passout year required"),
    EDUCATION_GAP_REQUIRED("Specify the number of educational gap you have"),
    REASON_FOR_EDUCATIONAL_GAP_REQUIRED("Please mention the reason for educational gap"),
    NUMBER_OF_CURRENT_BACKLOG_REQUIRED("Number of current backlog required"),
    INVALID_NUMBER_OF_CURRENT_BACKLOG("Invalid number of current backlog");


    private String educationError;
    EducationError(String educationError) { this.educationError=educationError; }
    public String getEducationError() { return this.educationError; }

}
