package com.recruitme.enums.error;

public enum DriveError {

    REQUIRED_DRIVE_ID("Drive id required"),
    INVALID_DRIVE_ID("Invalid drive id"),
    DRIVE_NAME_REQUIRED("Drive name requried"),
    COMPANY_REQUIRED("Company required"),
    INVALID_MIN_PACKAGE("Invalid minimum package"),
    INVALID_MAX_PACKAGE("Invalid maximum package"),
    MIN_PACKAGE_REQUIRED("Minimum package required"),
    MAX_PACKAGE_REQUIRED("Maximum package required"),
    MIN_PACKAGE_LESS_THAN_MAX_PACKAGE("Min package should be less than Max package"),
    VALID_PROGRAM_LIST_REQUIRED("Programs for drive required"),
    VALID_BRANCH_LIST_REQUIRED("Branchs for drive required"),
    INVALID_VALID_PROGRAM_LIST("Invalid programs"),
    INVALID_VALID_BRANCH_LIST("Invalid branches"),
    REQUIRED_10TH_MARKS("Required 10th CGPA and percentage"),
    REQUIRED_10TH_CGPA("10th CGPA required"),
    REQUIRED_10TH_PERCENTAGE("10th percentage required"),
    REQUIRED_12TH_MARKS("12Percenatge required"),
    REQUIRED_12TH_CGPA("12th CGPA required"),
    REQUIRED_12TH_PERCENTAGE("12th percentage required"),
    REQUIRED_UG_MARKS("Required UG CGPA and percentage"),
    REQUIRED_UG_CGPA("UG CGPA required"),
    REQUIRED_UG_PERCENTAGE("UG percentage required"),
    REQUIRED_PG_CGPA("PG CGPA required"),
    REQUIRED_PG_PERCENTAGE("PG percentage required"),
    INVALID_MARKS("Invalid marks"),
    MINIMUM_BACKLOG_ALLOWED_REQUIRED("Minimum number of backlog required"),
    INVALID_MINIMUM_BACKLOG_ALLOWED("Value should be >=0"),
    LAST_DATE_TO_APPLY_REQUIRED("Last date to apply required"),
    INVALID_LAST_DATE_TO_APPLY("Invalid date"),
    VACANCY_TYPE_REQUIRED("Vacancy type required"),
    UPLOAD_BY_ID_REQUIRED("Uploaded by id required"),
    INVALID_UPLOAD_BY_ID("Invalid upload by id"),
    DRIVE_FOR_GENDER_REQUIRED("Specify the gender for which this drive allowed"),
    DRIVE_FOR_GRADUATION_REQUIRED("Specify the graduation for which this drive allowed"),
    UG_PASSOUT_YEAR_ALLOW_REQUIRED("Mention passout year of all UG courses who are allowed for this drive"),
    PG_PASSOUT_YEAR_ALLOW_REQUIRED("Mention passout year of all PG courses who are allowed for this drive"),
    MINIMUM_GAP_ALLOWED_IN_EDUCATION_REQUIRED("Min gap allowed in education is required"),
    INVALID_MINIMUM_GAP_ALLOWED_IN_EDUCATION("Invalid min gap allowed in education");


    private String driveError;
    DriveError(String driveError) { this.driveError=driveError; }
    public String getDriveError() { return this.driveError; }
}
