package com.recruitme.enums.error;

import com.recruitme.domain.InterviewExperience;

public enum InterviewExperienceError {
    STUDENT_ID_REQUIRED("Student id required"),
    INVALID_STUDENT_ID("Invalid student id"),
    SALARY_REQUIRED("Salary required"),
    INVALID_SALARY("Invalid salary"),
    EXPERIENCE_REQUIRED("Experience required"),
    VACANCY_TYPE_REQUIRED("Select at least one vacancy type"),
    COMPANY_NAME_REQUIRED("Company name required"),
    PASSOUT_YEAR_REQUIRED("passout year required"),
    INVALID_PASSOUT_YEAR("Invalid passout year"),
    GRADUATION_TYPE_REQUIRED("Graducation type required");

    private String interviewExperienceError;
    InterviewExperienceError(String interviewExperienceError) { this.interviewExperienceError=interviewExperienceError; }
    public String getInterviewExperienceError() { return interviewExperienceError; }
}
