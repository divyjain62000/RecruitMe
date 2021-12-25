package com.recruitme.enums;

public enum JobType {
    FULLTIME("FullTime"),
    INTERNSHIP("Internship"),
    INTERNSHIP_AND_FULLTIME("Internship & Fulltime");

    private String jobType;
    JobType(String jobType){ this.jobType=jobType; }
    public String getJobType() { return this.jobType; }
}
